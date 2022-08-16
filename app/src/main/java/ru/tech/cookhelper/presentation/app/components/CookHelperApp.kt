package ru.tech.cookhelper.presentation.app.components

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.SignalWifiConnectedNoInternet4
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.olshevski.navigation.reimagined.*
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.utils.ConnectionUtils.isOnline
import ru.tech.cookhelper.presentation.all_images.AllImagesScreen
import ru.tech.cookhelper.presentation.app.viewModel.MainViewModel
import ru.tech.cookhelper.presentation.authentication.AuthenticationScreen
import ru.tech.cookhelper.presentation.chat.ChatScreen
import ru.tech.cookhelper.presentation.chat_list.ChatListScreen
import ru.tech.cookhelper.presentation.dish_details.DishDetailsScreen
import ru.tech.cookhelper.presentation.dishes_based_on_fridge.OnFridgeBasedDishes
import ru.tech.cookhelper.presentation.fullscreen_image_pager.FullScreenPagerScreen
import ru.tech.cookhelper.presentation.home_screen.HomeScreen
import ru.tech.cookhelper.presentation.post_creation.PostCreationScreen
import ru.tech.cookhelper.presentation.profile.ProfileScreen
import ru.tech.cookhelper.presentation.recipe_post_creation.RecipePostCreationScreen
import ru.tech.cookhelper.presentation.recipe_post_creation.components.CategorySelectionDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.LeaveUnsavedDataDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.PickProductsWithMeasuresDialog
import ru.tech.cookhelper.presentation.settings.SettingsScreen
import ru.tech.cookhelper.presentation.ui.theme.ProKitchenTheme
import ru.tech.cookhelper.presentation.ui.utils.*
import ru.tech.cookhelper.presentation.ui.utils.StateUtils.computedStateOf
import ru.tech.cookhelper.presentation.ui.utils.StatusBarUtils.showSystemBars
import ru.tech.cookhelper.presentation.ui.utils.provider.*

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun CookHelperApp(activity: ComponentActivity, viewModel: MainViewModel = viewModel()) {

    val fancyToastValues = remember { mutableStateOf(FancyToastValues()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val dialogController = rememberNavController<Dialog>(startDestination = Dialog.None)

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
            LocalScreenController provides viewModel.screenController,
            LocalDialogController provides dialogController,
            LocalSnackbarHost provides snackbarHostState,
            LocalToastHost provides fancyToastValues,
            LocalSettingsProvider provides viewModel.settingsState.value
        )
    ) {
        ProKitchenTheme {
            val screenController = LocalScreenController.current

            val showTopBar by computedStateOf { screenController.currentDestination!!::class.name !in hideTopBarList }

            BackHandler { dialogController.show(Dialog.Exit) }

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
                                viewModel.title =
                                    if (it is Screen.Home) Screen.Home.Recipes.title
                                    else it.title
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
                                    when (screenController.currentDestination) {
                                        is Screen.Settings -> {
                                            IconButton(
                                                onClick = { dialogController.show(Dialog.AboutApp) },
                                                content = {
                                                    Icon(
                                                        Icons.Outlined.HelpOutline,
                                                        null
                                                    )
                                                }
                                            )
                                        }
                                        is Screen.Profile -> {
                                            IconButton(
                                                onClick = { dialogController.show(Dialog.Logout) },
                                                content = { Icon(Icons.Outlined.Logout, null) }
                                            )
                                        }
                                        else -> {}
                                    }
                                },
                                title = {
                                    Text(
                                        viewModel.title.asString(),
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                scrollBehavior = scrollBehavior
                            )
                        }
                        AnimatedNavHost(
                            controller = screenController,
                            transitionSpec = ScaleCrossfadeTransitionSpec
                        ) { screen ->
                            Box(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
                                when (screen) {
                                    is Screen.Home -> {
                                        HomeScreen(
                                            onTitleChange = {
                                                viewModel.title = screen.title
                                            }
                                        )
                                    }
                                    is Screen.Favourites -> Placeholder(
                                        screen.baseIcon,
                                        screen.title.asString()
                                    )
                                    is Screen.RecipeDetails -> {
                                        DishDetailsScreen(
                                            id = screen.id,
                                            onBack = { screenController.pop() })
                                    }
                                    is Screen.MatchedRecipes -> {
                                        OnFridgeBasedDishes(
                                            onRecipeClicked = {
                                                screenController.navigate(
                                                    Screen.RecipeDetails(it)
                                                )
                                            },
                                            onBack = { screenController.pop() }
                                        )
                                    }
                                    is Screen.FullscreenImagePager -> {
                                        FullScreenPagerScreen(
                                            images = screen.images,
                                            initialId = screen.id,
                                            onBack = {
                                                screenController.pop()
                                                activity.showSystemBars()
                                            }
                                        )
                                    }
                                    is Screen.Settings -> {
                                        SettingsScreen(
                                            settingsState = viewModel.settingsState.value,
                                            onAction = { id, option ->
                                                viewModel.insertSetting(
                                                    id,
                                                    option
                                                )
                                            }
                                        )
                                    }
                                    is Screen.Profile -> {
                                        ProfileScreen(
                                            updateTitle = { newTitle ->
                                                viewModel.title = UIText.DynamicString(newTitle)
                                            }
                                        )
                                    }
                                    is Screen.AllImages -> {
                                        AllImagesScreen(
                                            images = screen.images,
                                            canAddImages = screen.canAddImages,
                                            onBack = { screenController.pop() },
                                            onAddImage = screen.onAddImage
                                        )
                                    }
                                    is Screen.BlockList -> Placeholder(
                                        screen.baseIcon,
                                        screen.title.asString()
                                    )
                                    is Screen.Cart -> Placeholder(
                                        screen.baseIcon,
                                        screen.title.asString()
                                    )
                                    is Screen.ChatList -> {
                                        ChatListScreen()
                                    }
                                    is Screen.Chat -> {
                                        ChatScreen(
                                            chatId = screen.chatId,
                                            onBack = { screenController.pop() }
                                        )
                                    }
                                    is Screen.Authentication -> AuthenticationScreen()
                                    is Screen.PostCreation -> {
                                        PostCreationScreen(
                                            onBack = {
                                                screenController.pop()
                                            },
                                            initialImageUri = screen.imageUri
                                        )
                                    }
                                    is Screen.RecipePostCreation -> {
                                        RecipePostCreationScreen(
                                            onBack = {
                                                screenController.pop()
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                DialogNavHost(controller = dialogController) { dialog ->
                    when (dialog) {
                        is Dialog.Exit -> {
                            ExitDialog(onExit = { activity.finishAffinity() })
                        }
                        is Dialog.AboutApp -> {
                            AboutAppDialog()
                        }
                        is Dialog.Logout -> {
                            val toastHost = LocalToastHost.current
                            LogoutDialog(
                                onLogout = {
                                    if (activity.isOnline()) {
                                        viewModel.logOut()
                                    } else toastHost.sendToast(
                                        Icons.Outlined.SignalWifiConnectedNoInternet4,
                                        message = activity.getString(R.string.no_connection)
                                    )
                                    dialogController.close()
                                }
                            )
                        }
                        is Dialog.PickProducts -> {
                            TODO()
                        }
                        is Dialog.CategorySelection -> {
                            CategorySelectionDialog(
                                categories = dialog.categories,
                                selectedCategory = dialog.selectedCategory,
                                onCategorySelected = dialog.onCategorySelected
                            )
                        }
                        is Dialog.LeaveUnsavedData -> {
                            LeaveUnsavedDataDialog(
                                title = dialog.title,
                                message = dialog.message,
                                onLeave = dialog.onLeave
                            )
                        }
                        is Dialog.PickProductsWithMeasures -> {
                            PickProductsWithMeasuresDialog(
                                products = dialog.products,
                                allProducts = dialog.allProducts,
                                onProductsPicked = dialog.onProductsPicked
                            )
                        }
                        else -> {}
                    }
                }

                FancyToast(fancyToastValues.value)
            }
        }
    }
}

private fun Modifier.navigationBarsLandscapePadding(): Modifier = composed {
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        navigationBarsPadding()
    } else this
}

@OptIn(ExperimentalAnimationApi::class)
internal val ScaleCrossfadeTransitionSpec = AnimatedNavHostTransitionSpec<Any?> { _, _, _ ->
    fadeIn() + scaleIn(initialScale = 0.85f) with fadeOut(tween(durationMillis = 50)) + scaleOut(
        targetScale = 0.85f
    )
}
