package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.popAll
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.app.viewModel.MainViewModel
import ru.tech.cookhelper.presentation.ui.utils.ResUtils.iconWith
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.drawerList
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.isCurrentDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainModalDrawerContent(
    viewModel: MainViewModel,
    drawerState: DrawerState,
    onClick: (screen: Screen) -> Unit
) {
    val screenController = LocalScreenController.current
    val scope = rememberCoroutineScope()

    LazyColumn(contentPadding = WindowInsets.systemBars.asPaddingValues()) {
        item {
            MainModalDrawerHeader(
                userState = viewModel.userState.value,
                onClick = {
                    screenController.navigate(Screen.Profile)
                    scope.launch { drawerState.close() }
                }
            )
        }

        items(drawerList) { item ->
            val selected = item.isCurrentDestination

            NavigationDrawerItem(
                icon = { Icon(item iconWith selected, null) },
                shape = RoundedCornerShape(
                    topStart = 0.0.dp,
                    topEnd = 36.0.dp,
                    bottomEnd = 36.0.dp,
                    bottomStart = 0.0.dp
                ),
                modifier = Modifier.padding(end = 12.dp),
                label = { Text(item.title.asString()) },
                selected = selected,
                onClick = {
                    onClick(item)
                    screenController.apply{
                        navigate(item)
                        popAll()
                    }
                    scope.launch { drawerState.close() }
                }
            )
            if (item is Screen.Home || item is Screen.BlockList) {
                Spacer(Modifier.size(10.dp))
                Divider(color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.size(10.dp))
            }
        }
    }
}
