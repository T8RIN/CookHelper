package ru.tech.cookhelper.presentation.app.components

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.olshevski.navigation.reimagined.AnimatedNavHostTransitionSpec
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.rememberNavController
import kotlinx.coroutines.launch
import ru.tech.cookhelper.core.utils.ReflectionUtils.name
import ru.tech.cookhelper.presentation.app.viewModel.MainViewModel
import ru.tech.cookhelper.presentation.ui.theme.ProKitchenTheme
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.findActivity
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.computedStateOf
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectOnLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.navigation.hideTopBarList
import ru.tech.cookhelper.presentation.ui.utils.provider.*

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
)
@Composable
fun CookHelperApp(viewModel: MainViewModel = viewModel()) {

    val scope = rememberCoroutineScope()
    val activity = LocalContext.current.findActivity()

    val fancyToastValues = remember { mutableStateOf(FancyToastValues()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val dialogController = rememberNavController<Dialog>(startDestination = Dialog.None)
    val screenController = rememberNavController<Screen>(startDestination = Screen.Home.None)

    val topAppBarActions: MutableState<(@Composable RowScope.() -> Unit)?> =
        remember { mutableStateOf(null) }
    LaunchedEffect(screenController.currentDestination) { topAppBarActions.clearActions() }

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior by remember {
        mutableStateOf(
            TopAppBarDefaults.pinnedScrollBehavior(
                topAppBarState
            )
        )
    }

    CompositionLocalProvider(
        values = arrayOf(
            LocalScreenController provides screenController,
            LocalDialogController provides dialogController,
            LocalSnackbarHost provides snackbarHostState,
            LocalToastHost provides fancyToastValues,
            LocalSettingsProvider provides viewModel.settingsState.value,
            LocalTopAppBarActions provides topAppBarActions
        )
    ) {
        ProKitchenTheme {
            val showTopBar by computedStateOf { screenController.currentDestination!!::class.name !in hideTopBarList }

            BackHandler { dialogController.show(Dialog.Exit(onExit = { activity?.finishAffinity() })) }

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsLandscapePadding(),
                color = MaterialTheme.colorScheme.background
            ) {
                ModalNavigationDrawer(
                    drawerContent = {
                        MainModalDrawerContent(
                            userState = viewModel.userState.value,
                            drawerState = drawerState,
                            onClick = {
                                viewModel.updateTitle(
                                    if (it is Screen.Home) Screen.Home.Recipes.title
                                    else it.title
                                )
                                screenController.navigateAndPopAll(it)
                            }
                        )
                    },
                    drawerState = drawerState,
                    gesturesEnabled = showTopBar
                ) {
                    Column {
                        AnimatedVisibility(visible = showTopBar) {
                            TopAppBar(
                                size = Size.Centered,
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch { drawerState.open() }
                                    }) {
                                        Icon(Icons.Rounded.Menu, null)
                                    }
                                },
                                actions = {
                                    topAppBarActions(this)
                                },
                                title = {
                                    Text(
                                        viewModel.title.value.asString(),
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                scrollBehavior = scrollBehavior
                            )
                        }
                        ScreenNavigationBox(
                            nestedScrollConnection = scrollBehavior.nestedScrollConnection,
                            controller = screenController,
                            transitionSpec = ScaleCrossfadeTransitionSpec,
                            onTitleChange = { viewModel.updateTitle(it) }
                        )
                    }
                }

                DialogNavigationBox(controller = dialogController)

                FancyToastHost(fancyToastValues.value)
            }
        }
    }

    viewModel.eventFlow.collectOnLifecycle {
        when (it) {
            is Event.NavigateIf -> {
                if (it.predicate(screenController.currentDestination)) screenController.navigate(it.screen)
            }
            is Event.NavigateTo -> screenController.navigate(it.screen)
            else -> {}
        }
    }
}

private fun Modifier.navigationBarsLandscapePadding(): Modifier = composed {
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        navigationBarsPadding()
    } else this
}

@OptIn(ExperimentalAnimationApi::class)
val ScaleCrossfadeTransitionSpec = AnimatedNavHostTransitionSpec<Any?> { _, _, _ ->
    (fadeIn() + scaleIn(initialScale = 0.85f))
        .with(fadeOut(tween(durationMillis = 50)) + scaleOut(targetScale = 0.85f))
}
