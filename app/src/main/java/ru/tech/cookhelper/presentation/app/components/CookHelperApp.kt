package ru.tech.cookhelper.presentation.app.components

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.olshevski.navigation.reimagined.rememberNavController
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.app.viewModel.MainViewModel
import ru.tech.cookhelper.presentation.ui.theme.CookHelperTheme
import ru.tech.cookhelper.presentation.ui.theme.ScaleCrossfadeTransitionSpec
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.findActivity
import ru.tech.cookhelper.presentation.ui.utils.compose.TopAppBarUtils.topAppBarScrollBehavior
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectEvents
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.*
import ru.tech.cookhelper.presentation.ui.utils.provider.TopAppBarVisuals.Companion.rememberTopAppBarVisuals
import ru.tech.cookhelper.presentation.ui.widgets.*
import ru.tech.cookhelper.presentation.ui.widgets.bottomsheet.BottomSheetValue
import ru.tech.cookhelper.presentation.ui.widgets.bottomsheet.rememberBottomSheetState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CookHelperApp(viewModel: MainViewModel = viewModel()) {

    val scope = rememberCoroutineScope()
    val activity = LocalContext.current.findActivity()

    val toastHostState = rememberToastHostState()
    val snackbarHostState = rememberSnackbarHostState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val screenController = rememberNavController<Screen>(startDestination = Screen.Home.None)
    val bottomSheetController = BottomSheetController(
        rememberNavController(initialBackstack = emptyList()),
        rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    val scrollBehavior = topAppBarScrollBehavior()
    val showTopAppBar = screenController.currentDestination?.showTopAppBar == true
    val topAppBarVisuals = rememberTopAppBarVisuals()
    LaunchedEffect(screenController.currentDestination) {
        topAppBarVisuals.clear()
    }

    val density = Density(
        LocalDensity.current.density,
        viewModel.settingsState.fontScale
    )

    CompositionLocalProvider(
        values = arrayOf(
            LocalScreenController provides screenController,
            LocalBottomSheetController provides bottomSheetController,
            LocalSnackbarHost provides snackbarHostState,
            LocalToastHostState provides toastHostState,
            LocalSettingsProvider provides viewModel.settingsState,
            LocalTopAppBarVisuals provides topAppBarVisuals,
            LocalDensity provides density
        )
    ) {
        CookHelperTheme {
            activity?.dialogBackHandler()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                ModalNavigationDrawer(
                    drawerContent = {
                        MainModalDrawerContent(
                            userState = viewModel.userState,
                            drawerState = drawerState,
                            onClick = {
                                viewModel.updateTitle(
                                    if (it is Screen.Home) Screen.Home.Feed.title
                                    else it.title
                                )
                                screenController.navigateAndPopAll(it)
                            }
                        )
                    },
                    drawerState = drawerState,
                    gesturesEnabled = showTopAppBar
                ) {
                    BottomSheetHost {
                        SimpleScaffold(
                            topAppBar = {
                                AnimatedTopAppBar(
                                    topAppBarSize = TopAppBarSize.Centered,
                                    navigationIcon = {
                                        NavIcon(
                                            onClick = { scope.launch { drawerState.open() } }
                                        )
                                    },
                                    actions = { AppBarActions() },
                                    title = {
                                        AppBarTitle(
                                            targetState = topAppBarVisuals.title to viewModel.title
                                        ) {
                                            it.first?.invoke() ?: Text(
                                                it.second.asString(),
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    },
                                    visible = showTopAppBar,
                                    scrollBehavior = scrollBehavior
                                )
                            },
                            content = {
                                ScreenHost(
                                    scrollBehavior = scrollBehavior,
                                    controller = screenController,
                                    transitionSpec = ScaleCrossfadeTransitionSpec,
                                    onTitleChange = { viewModel.updateTitle(it) }
                                )
                            }
                        )
                    }
                }
                ToastHost()
            }
        }
    }

    viewModel.collectEvents {
        when (it) {
            is Event.NavigateIf -> {
                if (it.predicate(screenController.currentDestination)) screenController.navigate(it.screen)
            }
            is Event.NavigateTo -> screenController.navigate(it.screen)
            else -> {}
        }
    }
}

@Composable
private fun Activity?.dialogBackHandler(onBack: () -> Unit = {}) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    BackHandler {
        showDialog = true
        onBack()
    }
    if (showDialog) ExitDialog(
        onExit = { this?.finishAffinity() },
        onDismissRequest = { showDialog = false }
    )
}