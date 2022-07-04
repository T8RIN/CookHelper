package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.app.viewModel.MainViewModel
import ru.tech.cookhelper.presentation.ui.utils.ResUtils.iconWith
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.UIText
import ru.tech.cookhelper.presentation.ui.utils.clearState
import ru.tech.cookhelper.presentation.ui.utils.drawerList
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.isCurrentScreen
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainModalDrawerContent(viewModel: MainViewModel, drawerState: DrawerState) {
    val screenController = LocalScreenController.current
    val scope = rememberCoroutineScope()

    LazyColumn(contentPadding = WindowInsets.systemBars.asPaddingValues()) {
        item {
            MainModalDrawerHeader(
                userState = viewModel.userState.value,
                onClick = {
                    screenController.navigate(Screen.Profile)
                    scope.launch { drawerState.close() }
                    clearState(all = true)
                }
            )
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
                    viewModel.title = UIText.StringResource(item.title)
                    screenController.navigate(item)

                    if (item is Screen.Home) {
                        viewModel.title = UIText.StringResource(Screen.Profile.title)
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
}
