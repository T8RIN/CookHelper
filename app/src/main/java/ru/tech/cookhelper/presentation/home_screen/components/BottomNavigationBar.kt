package ru.tech.cookhelper.presentation.home_screen.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.tech.cookhelper.presentation.ui.utils.ResUtils.iconWith
import ru.tech.cookhelper.presentation.ui.utils.Screen

@Composable
fun <T : Screen> BottomNavigationBar(
    selectedItem: T,
    items: List<T>,
    onClick: (screen: T) -> Unit
) {
    Surface(
        color = TopAppBarDefaults.smallTopAppBarColors()
            .containerColor(100f).value
    ) {
        NavigationBar(modifier = Modifier.navigationBarsPadding()) {
            items.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            screen iconWith (selectedItem == screen),
                            null
                        )
                    },
                    alwaysShowLabel = false,
                    label = {
                        Text(screen.shortTitle.asString())
                    },
                    selected = selectedItem == screen,
                    onClick = {
                        if (selectedItem != screen) onClick(screen)
                    }
                )
            }
        }
    }
}