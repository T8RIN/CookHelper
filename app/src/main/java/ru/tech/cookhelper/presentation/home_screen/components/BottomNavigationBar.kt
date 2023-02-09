package ru.tech.cookhelper.presentation.home_screen.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.getIcon
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen

@Composable
fun <T : Screen> BottomNavigationBar(
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    selectedItem: T,
    items: List<T>,
    onClick: (screen: T) -> Unit
) {
    NavigationBar(windowInsets = windowInsets) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        screen.getIcon(selectedItem == screen),
                        null
                    )
                },
                alwaysShowLabel = false,
                label = {
                    Text(
                        screen.shortTitle.asString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = selectedItem == screen,
                onClick = {
                    if (selectedItem != screen) onClick(screen)
                }
            )
        }
    }
}