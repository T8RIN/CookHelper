package ru.tech.prokitchen.presentation.app.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FindReplace
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.tech.prokitchen.R
import ru.tech.prokitchen.presentation.app.viewModel.MainViewModel
import ru.tech.prokitchen.presentation.dish_details.DishDetailsScreen
import ru.tech.prokitchen.presentation.dishes_based_on_fridge.OnFridgeBasedDishes
import ru.tech.prokitchen.presentation.favourite_dishes.FavouriteListScreen
import ru.tech.prokitchen.presentation.fridge_list.FridgeScreen
import ru.tech.prokitchen.presentation.recipes_list.RecipesList
import ru.tech.prokitchen.presentation.settings.SettingsScreen
import ru.tech.prokitchen.presentation.ui.theme.ProKitchenTheme
import ru.tech.prokitchen.presentation.ui.utils.*
import ru.tech.prokitchen.presentation.ui.utils.Screen.Favourites.asString
import ru.tech.prokitchen.presentation.ui.utils.provider.*

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun ProKitchenApp(activity: ComponentActivity, viewModel: MainViewModel = viewModel()) {
    CompositionLocalProvider(
        LocalSettingsProvider provides viewModel.settingsState.value
    ) {
        ProKitchenTheme {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            CompositionLocalProvider(
                values = arrayOf(
                    LocalScreenController provides viewModel.currentScreen,
                    LocalDialogController provides viewModel.currentDialog
                )
            ) {
                val screenController = LocalScreenController.current
                val dialogController = LocalDialogController.current

                val inNavigationMode by derivedStateOf {
                    screenController.currentScreen !is Screen.RecipeDetails && screenController.currentScreen !is Screen.MatchedRecipes
                }

                BackHandler {
                    dialogController.show(Dialog.Exit)
                }

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
                                            model = "https://kastybiy.herokuapp.com/static/img/recipe_1.jpg",
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
                                    val selected = screenController.currentScreen == item

                                    NavigationDrawerItem(
                                        icon = {
                                            Icon(
                                                if (selected) item.selectedIcon else item.baseIcon,
                                                null
                                            )
                                        },
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

                                            scope.launch {
                                                drawerState.animateTo(DrawerValue.Closed, tween())
                                            }
                                            clearState()
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
                                val backgroundColors = TopAppBarDefaults.smallTopAppBarColors()
                                val backgroundColor = backgroundColors.containerColor(
                                    scrollFraction = viewModel.scrollBehavior.scrollFraction
                                ).value
                                val foregroundColors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = Color.Transparent,
                                    scrolledContainerColor = Color.Transparent
                                )
                                Surface(color = backgroundColor) {
                                    CenterAlignedTopAppBar(
                                        modifier = Modifier.statusBarsPadding(),
                                        navigationIcon = {
                                            IconButton(onClick = {
                                                scope.launch {
                                                    drawerState.animateTo(
                                                        DrawerValue.Open,
                                                        tween()
                                                    )
                                                }
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
                                                    Text(viewModel.title.asString())
                                                } else {
                                                    SearchBar(
                                                        searchString = viewModel.searchString.value,
                                                        onValueChange = {
                                                            viewModel.updateSearch(
                                                                it
                                                            )
                                                        }
                                                    )
                                                }
                                            }
                                        },
                                        scrollBehavior = viewModel.scrollBehavior,
                                        colors = foregroundColors
                                    )
                                }
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
                                                                    if (screen.baseIcon != Icons.Outlined.PhoneAndroid) {
                                                                        Icon(
                                                                            if (viewModel.selectedItem == index) screen.selectedIcon else screen.baseIcon,
                                                                            null
                                                                        )
                                                                    } else {
                                                                        Icon(
                                                                            painterResource(if (viewModel.selectedItem == index) R.drawable.fridge else R.drawable.fridge_outline),
                                                                            null
                                                                        )
                                                                    }
                                                                },
                                                                alwaysShowLabel = false,
                                                                label = { Text(screen.shortTitle.asString()) },
                                                                selected = viewModel.selectedItem == index,
                                                                onClick = {
                                                                    if (viewModel.selectedItem != index) {
                                                                        viewModel.title =
                                                                            screen.title
                                                                        viewModel.selectedItem =
                                                                            index

                                                                        viewModel.navDestination =
                                                                            screen

                                                                        viewModel.searchMode = false
                                                                        viewModel.updateSearch("")

                                                                        clearState()
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
                                                                        if (it == SnackbarResult.ActionPerformed) viewModel.reload()
                                                                    }
                                                                } else if (viewModel.productsList.value.list?.isNotEmpty() == true) {
                                                                    dialogController.show(Dialog.PickProducts)
                                                                } else {
                                                                    showSnackbar(
                                                                        scope,
                                                                        snackbarHostState,
                                                                        "Суыткыч тулысынча тулы",
                                                                        ""
                                                                    ) {}
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
                                                            })
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
                                                            snackbarHostState,
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

                                                    }
                                                    else -> {}
                                                }
                                            }

                                        }
                                    }
                                    is Screen.Favourites -> {
                                        FavouriteListScreen(
                                            snackState = snackbarHostState,
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
                                        val back: () -> Unit =
                                            { screenController.navigate(screen.previousScreen) }
                                        BackHandler { back() }
                                        OnFridgeBasedDishes(
                                            onRecipeClicked = {
                                                screenController.navigate(
                                                    Screen.RecipeDetails(
                                                        it,
                                                        screen
                                                    )
                                                )
                                            },
                                            goBack = { back() })
                                    }
                                    is Screen.Settings -> {
                                        SettingsScreen(viewModel.settingsState.value) { id, option ->
                                            viewModel.insertSetting(id, option)
                                        }
                                    }
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
                                            Text(stringResource(R.string.okk))
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
                                                                state.list[index].name.replaceFirstChar { it.uppercase() },
                                                                textAlign = TextAlign.Start,
                                                                style = MaterialTheme.typography.bodyLarge,
                                                                modifier = Modifier.padding(
                                                                    horizontal = 10.dp
                                                                )
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
                                                    modifier = Modifier.align(
                                                        Alignment.Center
                                                    )
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
                        BackHandler {
                            viewModel.searchMode = false
                        }
                    }
                }
            }
        }
    }
}