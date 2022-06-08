package ru.tech.cookhelper.presentation.app.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.outlined.FindReplace
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.twotone.HourglassEmpty
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.viewModel.MainViewModel
import ru.tech.cookhelper.presentation.authentication.AuthenticationScreen
import ru.tech.cookhelper.presentation.dish_details.DishDetailsScreen
import ru.tech.cookhelper.presentation.dishes_based_on_fridge.OnFridgeBasedDishes
import ru.tech.cookhelper.presentation.favourite_dishes.FavouriteListScreen
import ru.tech.cookhelper.presentation.fridge_list.FridgeScreen
import ru.tech.cookhelper.presentation.recipes_list.RecipesList
import ru.tech.cookhelper.presentation.settings.SettingsScreen
import ru.tech.cookhelper.presentation.ui.theme.ProKitchenTheme
import ru.tech.cookhelper.presentation.ui.utils.*
import ru.tech.cookhelper.presentation.ui.utils.ResUtils.alternateIcon
import ru.tech.cookhelper.presentation.ui.utils.ResUtils.asString
import ru.tech.cookhelper.presentation.ui.utils.ResUtils.iconWith
import ru.tech.cookhelper.presentation.ui.utils.provider.*

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun CookHelperApp(activity: ComponentActivity, viewModel: MainViewModel = viewModel()) {
    val icon = remember { mutableStateOf(Icons.TwoTone.HourglassEmpty) }
    val text = remember { mutableStateOf("") }
    val changed = remember { mutableStateOf(false) }
    val length = remember { mutableStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    CompositionLocalProvider(
        LocalSettingsProvider provides viewModel.settingsState.value
    ) {
        ProKitchenTheme {
            CompositionLocalProvider(
                values = arrayOf(
                    LocalScreenController provides viewModel.currentScreen,
                    LocalDialogController provides viewModel.currentDialog,
                    LocalSnackbarHost provides snackbarHostState,
                    LocalToastHost provides FancyToastValues(icon, text, changed, length)
                )
            ) {
                val screenController = LocalScreenController.current
                val dialogController = LocalDialogController.current

                val inNavigationMode by derivedStateOf { screenController.currentScreen::class.name !in showTopBarList }

                BackHandler { dialogController.show(Dialog.Exit) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ModalNavigationDrawer(
                        drawerContent = {
                            LazyColumn(contentPadding = WindowInsets.systemBars.asPaddingValues()) {
                                item {
                                    Column {
                                        Picture(
                                            model = "https://sun9-76.userapi.com/s/v1/ig2/lbF4vZbkOi0zdhtU-5iXrF2YPHiwIVVZouCCGvQBb7MV7OKzhhPUg7KW4nyc7vr7SS7HVDDyV_kdPVeoPo7j8RHb.jpg?size=1620x2160&quality=95&type=album",
                                            modifier = Modifier
                                                .padding(start = 15.dp, top = 15.dp)
                                                .size(64.dp)
                                        )
                                        Spacer(Modifier.size(10.dp))
                                        Column(
                                            Modifier
                                                .height(52.dp)
                                                .padding(start = 15.dp)
                                        ) {
                                            Text(
                                                "Малик Мухаметзянов",
                                                style = MaterialTheme.typography.headlineSmall
                                            )
                                            Spacer(Modifier.weight(1f))
                                            Text(
                                                "@t8rin",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Spacer(Modifier.size(30.dp))
                                    }
                                }
                                itemsIndexed(drawerList) { _, item ->
                                    val selected = item.isCurrentScreen

                                    NavigationDrawerItem(
                                        icon = { Icon(item iconWith selected, null) },
                                        shape = RoundedCornerShape(
                                            topStart = 0.0.dp,
                                            topEnd = 36.0.dp,
                                            bottomEnd = 36.0.dp,
                                            bottomStart = 0.0.dp
                                        ),
                                        modifier = Modifier.padding(end = 12.dp),
                                        label = { Text(stringResource(item.title)) },
                                        selected = selected,
                                        onClick = {
                                            viewModel.title = item.title
                                            screenController.navigate(item)

                                            if (item is Screen.Home) {
                                                viewModel.title = Screen.Recipes.title
                                                viewModel.selectedItem = 0
                                                viewModel.navDestination = Screen.Recipes
                                            }
                                            scope.launch { drawerState.close() }
                                            clearState(all = true)
                                        }
                                    )
                                    if (item is Screen.Home || item is Screen.BlockList) {
                                        Spacer(Modifier.size(10.dp))
                                        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Spacer(Modifier.size(10.dp))
                                    }
                                }
                            }
                        },
                        drawerState = drawerState,
                        gesturesEnabled = inNavigationMode
                    ) {
                        Column {

                            AnimatedVisibility(visible = inNavigationMode) {
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
//                                            if (viewModel.selectedItem == 0 && screenController.currentScreen == Screen.Home && !viewModel.searchMode) {
//                                                val focus = LocalFocusManager.current
//                                                IconButton(onClick = {
//                                                    if (viewModel.searchMode) {
//                                                        focus.clearFocus()
//                                                        viewModel.updateSearch("")
//                                                    }
//                                                    viewModel.searchMode = !viewModel.searchMode
//                                                }) {
//                                                    Icon(
//                                                        if (!viewModel.searchMode) Icons.Rounded.Search else Icons.Rounded.ArrowBack,
//                                                        null
//                                                    )
//                                                }
//                                            } else
                                        if (screenController.currentScreen is Screen.Settings) {
                                            IconButton(onClick = { dialogController.show(Dialog.AboutApp) }) {
                                                Icon(Icons.Outlined.HelpOutline, null)
                                            }
                                        }
                                    },
                                    title = {
                                        Box {
                                            if (!viewModel.searchMode) {
                                                Text(viewModel.title.asString(activity))
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
                                    scrollBehavior = viewModel.scrollBehavior
                                )
                            }

                            AnimatedContent(
                                targetState = screenController.currentScreen, modifier = Modifier
                                    .nestedScroll(viewModel.scrollBehavior.nestedScrollConnection)
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
                                                                                screen.alternateIcon(viewModel.selectedItem == index)
                                                                            ), null
                                                                        )
                                                                    }
                                                                },
                                                                alwaysShowLabel = false,
                                                                label = { Text(screen.shortTitle.asString(activity)) },
                                                                selected = viewModel.selectedItem == index,
                                                                onClick = {
                                                                    if (viewModel.selectedItem != index) {
                                                                        viewModel.apply {
                                                                            title = screen.title
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
                                    is Screen.Settings -> {
                                        SettingsScreen(viewModel.settingsState.value) { id, option ->
                                            viewModel.insertSetting(id, option)
                                        }
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
                                    is Screen.Profile -> Placeholder(
                                        screen.baseIcon,
                                        stringResource(screen.title)
                                    )
                                    is Screen.Recipes -> Placeholder(
                                        screen.baseIcon,
                                        stringResource(screen.title)
                                    )
                                    is Screen.Authentication -> AuthenticationScreen()
                                }
                            }
                        }
                    }

                    AnimatedVisibility(visible = dialogController.currentDialog != Dialog.None) {
                        when (dialogController.currentDialog) {
                            is Dialog.Exit -> {
                                ExitDialog(activity)
                            }
                            is Dialog.AboutApp -> {
                                AboutAppDialog()
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
                                                    items(state.list.size) { index ->
                                                        Row(
                                                            Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Text(
                                                                state.list[index].name.uppercase(),
                                                                textAlign = TextAlign.Start,
                                                                style = MaterialTheme.typography.bodyLarge,
                                                                modifier = Modifier.padding(horizontal = 10.dp)
                                                            )
                                                            Spacer(Modifier.weight(1f))
                                                            Checkbox(
                                                                checked = viewModel.tempList.contains(
                                                                    state.list[index].id
                                                                ),
                                                                onCheckedChange = {
                                                                    if (it) viewModel.tempList.add(
                                                                        state.list[index].id
                                                                    )
                                                                    else viewModel.tempList.remove(
                                                                        state.list[index].id
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

                    LaunchedEffect(Unit) {
                        screenController.navigate(Screen.Authentication)
                    }

                    FancyToast(
                        icon = icon.value,
                        message = text.value,
                        changed = changed,
                        length = length.value
                    )
                }
            }
        }
    }
}