package ru.tech.cookhelper.presentation.chat_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.tech.cookhelper.presentation.chat_list.viewModel.ChatListViewModel
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.currentScreen
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@Composable
fun ChatListScreen(viewModel: ChatListViewModel = scopedViewModel()) {
    val screenController = LocalScreenController.current
    LaunchedEffect(Unit) {
        screenController.apply {
            navigate(Screen.Chat(chatId = "1", previousScreen = currentScreen))
        }
    }
}