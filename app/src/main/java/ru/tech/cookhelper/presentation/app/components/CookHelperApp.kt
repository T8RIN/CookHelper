package ru.tech.cookhelper.presentation.app.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.rememberNavController
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.app.viewModel.MainViewModel
import ru.tech.cookhelper.presentation.ui.theme.ProKitchenTheme
import ru.tech.cookhelper.presentation.ui.theme.ScaleCrossfadeTransitionSpec
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.findActivity
import ru.tech.cookhelper.presentation.ui.utils.compose.TopAppBarUtils.topAppBarScrollBehavior
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CookHelperApp(viewModel: MainViewModel = viewModel()) {

    val scope = rememberCoroutineScope()
    val activity = LocalContext.current.findActivity()

    val fancyToastValues = rememberFancyToastValues()
    val snackbarHostState = rememberSnackbarHostState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val dialogController = rememberNavController<Dialog>(startDestination = Dialog.None)
    val screenController = rememberNavController<Screen>(startDestination = Screen.Home.None)

    val showTopAppBar = screenController.currentDestination?.showTopAppBar == true
    val topAppBarActions: MutableState<(@Composable RowScope.() -> Unit)?> = remember {
        mutableStateOf(null)
    }
    LaunchedEffect(screenController.currentDestination) {
        topAppBarActions.clearActions()
    }

    val scrollBehavior = topAppBarScrollBehavior()

    CompositionLocalProvider(
        values = arrayOf(
            LocalScreenController provides screenController,
            LocalDialogController provides dialogController,
            LocalSnackbarHost provides snackbarHostState,
            LocalToastHost provides fancyToastValues,
            LocalSettingsProvider provides viewModel.settingsState,
            LocalTopAppBarActions provides topAppBarActions
        )
    ) {
        ProKitchenTheme {
            BackHandler {
                dialogController.show(
                    Dialog.Exit(onExit = { activity?.finishAffinity() })
                )
            }

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
                                    if (it is Screen.Home) Screen.Home.Recipes.title
                                    else it.title
                                )
                                screenController.navigateAndPopAll(it)
                            }
                        )
                    },
                    drawerState = drawerState,
                    gesturesEnabled = showTopAppBar
                ) {
                    Column {
                        AnimatedTopAppBar(
                            topAppBarSize = TopAppBarSize.Centered,
                            navigationIcon = {
                                IconButton(
                                    onClick = { scope.launch { drawerState.open() } },
                                    content = { Icon(Icons.Rounded.Menu, null) }
                                )
                            },
                            actions = { topAppBarActions(this) },
                            title = {
                                Text(
                                    viewModel.title.asString(),
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            visible = showTopAppBar,
                            scrollBehavior = scrollBehavior
                        )
                        ScreenHost(
                            nestedScrollConnection = scrollBehavior.nestedScrollConnection,
                            controller = screenController,
                            transitionSpec = ScaleCrossfadeTransitionSpec,
                            onTitleChange = { viewModel.updateTitle(it) }
                        )
                    }
                }

                DialogHost(controller = dialogController)

                FancyToastHost(fancyToastValues.value)
            }
        }
    }

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.NavigateIf -> {
                if (it.predicate(screenController.currentDestination)) screenController.navigate(it.screen)
            }
            is Event.NavigateTo -> screenController.navigate(it.screen)
            else -> {}
        }
    }
}