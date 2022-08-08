package ru.tech.cookhelper.presentation.app.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.outlined.FindReplace
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.SignalWifiConnectedNoInternet4
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.utils.ConnectionUtils.isOnline
import ru.tech.cookhelper.presentation.all_images.AllImagesScreen
import ru.tech.cookhelper.presentation.app.viewModel.MainViewModel
import ru.tech.cookhelper.presentation.authentication.AuthenticationScreen
import ru.tech.cookhelper.presentation.dish_details.DishDetailsScreen
import ru.tech.cookhelper.presentation.dishes_based_on_fridge.OnFridgeBasedDishes
import ru.tech.cookhelper.presentation.favourite_dishes.FavouriteListScreen
import ru.tech.cookhelper.presentation.fridge_list.FridgeScreen
import ru.tech.cookhelper.presentation.fullscreen_image_pager.FullScreenPagerScreen
import ru.tech.cookhelper.presentation.post_creation.PostCreationScreen
import ru.tech.cookhelper.presentation.profile.ProfileScreen
import ru.tech.cookhelper.presentation.recipes_list.RecipesList
import ru.tech.cookhelper.presentation.settings.SettingsScreen
import ru.tech.cookhelper.presentation.ui.theme.ProKitchenTheme
import ru.tech.cookhelper.presentation.ui.utils.*
import ru.tech.cookhelper.presentation.ui.utils.ResUtils.alternateIcon
import ru.tech.cookhelper.presentation.ui.utils.ResUtils.asString
import ru.tech.cookhelper.presentation.ui.utils.ResUtils.iconWith
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

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior by remember {
        mutableStateOf(
            TopAppBarDefaults.pinnedScrollBehavior(
                topAppBarState
            )
        )
    }

    CompositionLocalProvider(
        LocalSettingsProvider provides viewModel.settingsState.value
    ) {
        ProKitchenTheme {
            CompositionLocalProvider(
                values = arrayOf(
                    LocalScreenController provides viewModel.currentScreen,
                    LocalDialogController provides viewModel.currentDialog,
                    LocalSnackbarHost provides snackbarHostState,
                    LocalToastHost provides fancyToastValues
                )
            ) {
                val screenController = LocalScreenController.current
                val dialogController = LocalDialogController.current

                val showTopBar by computedStateOf { screenController.currentScreen::class.name !in hideTopBarList }

                BackHandler { dialogController.show(Dialog.Exit) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ModalNavigationDrawer(
                        drawerContent = { MainModalDrawerContent(viewModel, drawerState) },
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
                                        when (screenController.currentScreen) {
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
                                        Box {
                                            if (!viewModel.searchMode) {
                                                Text(
                                                    viewModel.title.asString(activity),
                                                    fontWeight = FontWeight.Medium
                                                )
                                            } else {
                                                SearchBar(
                                                    searchString = viewModel.searchString.value,
                                                    onValueChange = {
                                                        viewModel.updateSearch(it)
                                                    }
                                                )
                                            }
                                        }
                                    },
                                    scrollBehavior = scrollBehavior
                                )
                            }

                            AnimatedContent(
                                targetState = screenController.currentScreen, modifier = Modifier
                                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                            ) { screen ->
                                when (screen) {
                                    is Screen.Home -> {
                                        Scaffold(
                                            bottomBar = {
                                                Surface(
                                                    color = TopAppBarDefaults.smallTopAppBarColors()
                                                        .containerColor(100f).value
                                                ) {
                                                    NavigationBar(modifier = Modifier.navigationBarsPadding()) {
                                                        val items = navBarList

                                                        items.forEachIndexed { index, screen ->

                                                            if (viewModel.navDestination == screen) {
                                                                viewModel.selectedItem = index
                                                            }

                                                            NavigationBarItem(
                                                                icon = {
                                                                    if (screen.baseIcon != Icons.Default.PhoneAndroid) {
                                                                        Icon(
                                                                            screen iconWith (viewModel.selectedItem == index),
                                                                            null
                                                                        )
                                                                    } else {
                                                                        Icon(
                                                                            painterResource(
                                                                                screen.alternateIcon(
                                                                                    viewModel.selectedItem == index
                                                                                )
                                                                            ), null
                                                                        )
                                                                    }
                                                                },
                                                                alwaysShowLabel = false,
                                                                label = {
                                                                    Text(
                                                                        screen.shortTitle.asString(
                                                                            activity
                                                                        )
                                                                    )
                                                                },
                                                                selected = viewModel.selectedItem == index,
                                                                onClick = {
                                                                    if (viewModel.selectedItem != index) {
                                                                        viewModel.apply {
                                                                            title =
                                                                                UIText.StringResource(
                                                                                    screen.title
                                                                                )
                                                                            selectedItem = index
                                                                            navDestination = screen
                                                                            searchMode = false
                                                                            updateSearch("")
                                                                        }
                                                                        clearState(all = true)
                                                                    }
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                            },
                                            floatingActionButton = {
                                                AnimatedVisibility(
                                                    visible = viewModel.selectedItem == 2,
                                                    enter = fadeIn() + scaleIn(),
                                                    exit = fadeOut() + scaleOut()
                                                ) {
                                                    Column(horizontalAlignment = Alignment.End) {
                                                        SmallFloatingActionButton(
                                                            onClick = {
                                                                if (viewModel.productsList.value.error.isNotBlank()) {
                                                                    showSnackbar(
                                                                        scope,
                                                                        snackbarHostState,
                                                                        viewModel.productsList.value.error,
                                                                        "Яхшы"
                                                                    ) {
                                                                        if (it.clicked) viewModel.reload()
                                                                    }
                                                                } else if (viewModel.productsList.value.list?.isNotEmpty() == true) {
                                                                    dialogController.show(Dialog.PickProducts)
                                                                } else {
                                                                    showSnackbar(
                                                                        scope,
                                                                        snackbarHostState,
                                                                        "Я не знаю, че не так"
                                                                    )
                                                                }
                                                            },
                                                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                                                        ) {
                                                            Icon(Icons.Rounded.Add, null)
                                                        }
                                                        Spacer(Modifier.size(8.dp))
                                                        ExtendedFloatingActionButton(
                                                            onClick = {
                                                                if (viewModel.productsList.value.list != null) screenController.navigate(
                                                                    Screen.MatchedRecipes(Screen.Home)
                                                                )
                                                            },
                                                            text = {
                                                                Text(stringResource(R.string.match))
                                                            },
                                                            icon = {
                                                                Icon(
                                                                    Icons.Outlined.FindReplace,
                                                                    null
                                                                )
                                                            }
                                                        )
                                                    }
                                                }
                                            },
                                            snackbarHost = { SnackbarHost(snackbarHostState) },
                                        ) { contentPadding ->

                                            AnimatedContent(
                                                targetState = viewModel.navDestination,
                                                modifier = Modifier.padding(contentPadding)
                                            ) { deepScreen ->
                                                when (deepScreen) {
                                                    is Screen.Recipes -> {
                                                        RecipesList(
                                                            viewModel.searchString,
                                                            onRecipeClick = {
                                                                screenController.navigate(
                                                                    Screen.RecipeDetails(
                                                                        it,
                                                                        screen
                                                                    )
                                                                )
                                                            }
                                                        )
                                                    }
                                                    is Screen.Fridge -> {
                                                        FridgeScreen()
                                                    }
                                                    is Screen.Forum -> {
                                                        Placeholder(
                                                            deepScreen.baseIcon,
                                                            stringResource(deepScreen.title)
                                                        )
                                                    }
                                                    else -> {}
                                                }
                                            }

                                        }
                                    }
                                    is Screen.Favourites -> {
                                        FavouriteListScreen(
                                            onRecipeClicked = {
                                                screenController.navigate(
                                                    Screen.RecipeDetails(
                                                        it,
                                                        screen
                                                    )
                                                )
                                            }
                                        )
                                    }
                                    is Screen.RecipeDetails -> {
                                        val id = screen.id
                                        val back: () -> Unit =
                                            { screenController.navigate(screen.previousScreen) }
                                        BackHandler { back() }
                                        DishDetailsScreen(id, goBack = { back() })
                                    }
                                    is Screen.MatchedRecipes -> {
                                        val back: () -> Unit = {
                                            screenController.navigate(screen.previousScreen)
                                            clearState(Screen.MatchedRecipes::class.name)
                                        }
                                        BackHandler { back() }
                                        OnFridgeBasedDishes(
                                            onRecipeClicked = {
                                                screenController.navigate(
                                                    Screen.RecipeDetails(it, screen)
                                                )
                                            },
                                            goBack = { back() }
                                        )
                                    }
                                    is Screen.FullscreenImagePager -> {
                                        val back: () -> Unit = {
                                            screenController.navigate(screen.previousScreen)
                                            activity.showSystemBars()
                                        }
                                        BackHandler { back() }

                                        FullScreenPagerScreen(
                                            images = screen.images,
                                            initialId = screen.id,
                                            goBack = { back() }
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
                                        ProfileScreen(updateTitle = { newTitle ->
                                            viewModel.title = UIText.DynamicString(newTitle)
                                        })
                                    }
                                    is Screen.AllImages -> {
                                        val back: () -> Unit = {
                                            screenController.navigate(screen.previousScreen)
                                        }
                                        BackHandler { back() }

                                        AllImagesScreen(
                                            images = screen.images,
                                            canAddImages = screen.canAddImages,
                                            goBack = { back() },
                                            onAddImage = {}
                                        )
                                    }
                                    is Screen.BlockList -> Placeholder(
                                        screen.baseIcon,
                                        stringResource(screen.title)
                                    )
                                    is Screen.Cart -> Placeholder(
                                        screen.baseIcon,
                                        stringResource(screen.title)
                                    )
                                    is Screen.Forum -> Placeholder(
                                        screen.baseIcon,
                                        stringResource(screen.title)
                                    )
                                    is Screen.Fridge -> Placeholder(
                                        screen.baseIcon,
                                        stringResource(screen.title)
                                    )
                                    is Screen.Messages -> Placeholder(
                                        screen.baseIcon,
                                        stringResource(screen.title)
                                    )
                                    is Screen.Recipes -> Placeholder(
                                        screen.baseIcon,
                                        stringResource(screen.title)
                                    )
                                    is Screen.Authentication -> AuthenticationScreen()
                                    is Screen.PostCreation -> {
                                        PostCreationScreen(
                                            goBack = {
                                                screenController.navigate(screen.previousScreen)
                                            },
                                            initialImageUri = screen.imageUri
                                        )
                                    }
                                }
                            }
                        }
                    }

                    AnimatedVisibility(visible = dialogController.currentDialog != Dialog.None) {
                        when (dialogController.currentDialog) {
                            is Dialog.Exit -> {
                                ExitDialog(onExit = { activity.finishAffinity() })
                            }
                            is Dialog.AboutApp -> {
                                AboutAppDialog()
                            }
                            is Dialog.Logout -> {
                                val toastHost = LocalToastHost.current
                                LogoutDialog(onLogout = {
                                    if (activity.isOnline()) {
                                        viewModel.logOut()
                                    } else toastHost.sendToast(
                                        Icons.Outlined.SignalWifiConnectedNoInternet4,
                                        message = activity.getString(R.string.no_connection)
                                    )
                                    dialogController.close()
                                })
                            }
                            is Dialog.PickProducts -> {
                                val maxHeight = LocalConfiguration.current.screenHeightDp.dp * 0.75f

                                AlertDialog(
                                    modifier = Modifier.heightIn(max = maxHeight),
                                    onDismissRequest = {},
                                    confirmButton = {
                                        TextButton(onClick = {
                                            viewModel.processToFridge()
                                            dialogController.close()
                                        }) {
                                            Text(stringResource(R.string.okay))
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = { dialogController.close() }) {
                                            Text(stringResource(R.string.cancel))
                                        }
                                    },
                                    title = {
                                        Text(stringResource(R.string.pick_products))
                                    },
                                    text = {
                                        val state = viewModel.productsList.value

                                        Box(Modifier.fillMaxWidth()) {
                                            if (state.list != null && !state.isLoading) {
                                                LazyColumn {
                                                    items(state.list) { item ->
                                                        Row(
                                                            Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Text(
                                                                item.name.uppercase(),
                                                                textAlign = TextAlign.Start,
                                                                style = MaterialTheme.typography.bodyLarge,
                                                                modifier = Modifier.padding(
                                                                    horizontal = 10.dp
                                                                )
                                                            )
                                                            Spacer(Modifier.weight(1f))
                                                            Checkbox(
                                                                checked = viewModel.tempList.contains(
                                                                    item.id
                                                                ),
                                                                onCheckedChange = {
                                                                    if (it) viewModel.tempList.add(
                                                                        item.id
                                                                    )
                                                                    else viewModel.tempList.remove(
                                                                        item.id
                                                                    )
                                                                }
                                                            )
                                                        }
                                                        Spacer(Modifier.height(10.dp))
                                                    }
                                                }
                                            }

                                            if (state.isLoading) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            }
                                        }
                                    }
                                )
                            }
                            else -> {}
                        }
                    }

                    if (viewModel.searchMode && viewModel.searchString.value.isEmpty()) {
                        BackHandler { viewModel.searchMode = false }
                    }

                    FancyToast(fancyToastValues.value)
                }
            }
        }
    }
}