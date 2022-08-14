package ru.tech.cookhelper.presentation.chat_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.tech.cookhelper.presentation.chat_list.components.ChatListItem
import ru.tech.cookhelper.presentation.chat_list.viewModel.ChatListViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.name
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.currentScreen
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate
import ru.tech.cookhelper.presentation.ui.utils.rememberForeverLazyListState
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@Composable
fun ChatListScreen(viewModel: ChatListViewModel = scopedViewModel()) {
    val screenController = LocalScreenController.current
    val chatList = viewModel.chatList.value
    val scrollState = rememberForeverLazyListState(Screen.ChatList::class.name)

    Box {
        LazyColumn(modifier = Modifier.fillMaxSize(), state = scrollState) {
            itemsIndexed(chatList) { index, chat ->
                ChatListItem(
                    onClick = {
                        screenController.apply {
                            navigate(Screen.Chat(chatId = chat.id, previousScreen = currentScreen))
                        }
                    },
                    image = chat.image,
                    title = chat.title,
                    lastMessageText = chat.lastMessageText,
                    lastMessageTimestamp = chat.lastMessageTimestamp,
                    newMessagesCount = chat.newMessagesCount
                )
                if (index != chatList.lastIndex) Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }

        ExpandableFloatingActionButton(onClick = { /*TODO*/ }, expanded = fabExpanded)
    }

}