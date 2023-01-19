package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import ru.tech.cookhelper.presentation.ui.utils.compose.bottomsheet.BottomSheetValue
import ru.tech.cookhelper.presentation.ui.utils.compose.bottomsheet.rememberBottomSheetState
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectEvents
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.*
import ru.tech.cookhelper.presentation.ui.utils.provider.TopAppBarVisuals.Companion.rememberTopAppBarVisuals

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CookHelperApp(viewModel: MainViewModel = viewModel()) {

    val scope = rememberCoroutineScope()
    val activity = LocalContext.current.findActivity()

    val toastHostState = rememberToastHostState()
    val snackbarHostState = rememberSnackbarHostState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val dialogController = rememberNavController<Dialog>(initialBackstack = emptyList())
    val screenController = rememberNavController<Screen>(startDestination = Screen.Home.None)
    val bottomSheetController = BottomSheetController(
        rememberNavController(initialBackstack = emptyList()),
        rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    val showTopAppBar = screenController.currentDestination?.showTopAppBar == true
    val topAppBarVisuals = rememberTopAppBarVisuals()
    LaunchedEffect(screenController.currentDestination) {
        topAppBarVisuals.clear()
    }
    val scrollBehavior = topAppBarScrollBehavior()

    val density = Density(
        LocalDensity.current.density,
        viewModel.settingsState.fontScale
    )

    CompositionLocalProvider(
        values = arrayOf(
            LocalScreenController provides screenController,
            LocalDialogController provides dialogController,
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
                                        AnimatedContent(
                                            targetState = topAppBarVisuals.navigationIcon,
                                            transitionSpec = { fadeIn() + scaleIn() with fadeOut() + scaleOut() }
                                        ) {
                                            it?.invoke() ?: IconButton(
                                                onClick = { scope.launch { drawerState.open() } },
                                                content = { Icon(Icons.Rounded.Menu, null) }
                                            )
                                        }
                                    },
                                    actions = {
                                        AnimatedVisibility(
                                            visible = topAppBarVisuals.actions != null,
                                            enter = fadeIn() + scaleIn(),
                                            exit = fadeOut()
                                        ) {
                                            AnimatedContent(
                                                targetState = topAppBarVisuals.actions,
                                                transitionSpec = {
                                                    fadeIn() + scaleIn() with fadeOut() + scaleOut()
                                                }
                                            ) {
                                                Row { it?.invoke(this) }
                                            }
                                        }
                                    },
                                    title = {
                                        AnimatedContent(
                                            targetState = topAppBarVisuals.title to viewModel.title,
                                            transitionSpec = { fadeIn() with fadeOut() + scaleOut() }
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
                DialogHost()
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