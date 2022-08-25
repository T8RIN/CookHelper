package ru.tech.cookhelper.presentation.home_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.rememberNavController
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.home_screen.components.BottomNavigationBar
import ru.tech.cookhelper.presentation.ui.theme.ScaleCrossfadeTransitionSpec
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.navigation.navBarList
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalSnackbarHost
import ru.tech.cookhelper.presentation.ui.utils.provider.currentDestination
import ru.tech.cookhelper.presentation.ui.utils.provider.navigateAndPopAll

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(onTitleChange: (newTitle: UIText) -> Unit) {
    val bottomNavigationController =
        rememberNavController<Screen.Home>(startDestination = Screen.Home.Recipes)
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = bottomNavigationController.currentDestination
                    ?: Screen.Home.Recipes,
                items = navBarList,
                onClick = { screen ->
                    bottomNavigationController.navigateAndPopAll(screen)
                    onTitleChange(screen.title)
                }
            )
        },
        snackbarHost = { SnackbarHost(LocalSnackbarHost.current) }
    ) { contentPadding ->
        Box(Modifier.padding(contentPadding)) {
            AnimatedNavHost(
                controller = bottomNavigationController,
                transitionSpec = ScaleCrossfadeTransitionSpec
            ) { bottomNavScreen ->
                when (bottomNavScreen) {
                    is Screen.Home.Recipes -> {
                        Placeholder(
                            icon = bottomNavScreen.baseIcon,
                            text = bottomNavScreen.title.asString(),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                        )
                    }
                    is Screen.Home.Fridge -> {
                        Placeholder(
                            icon = bottomNavScreen.baseIcon,
                            text = bottomNavScreen.title.asString(),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                        )
                    }
                    is Screen.Home.Forum -> {
                        Placeholder(
                            bottomNavScreen.baseIcon,
                            bottomNavScreen.title.asString(),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}