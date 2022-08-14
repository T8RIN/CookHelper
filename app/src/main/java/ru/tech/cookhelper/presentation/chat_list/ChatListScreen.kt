package ru.tech.cookhelper.presentation.chat_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Loading
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.chat_list.components.ChatListItem
import ru.tech.cookhelper.presentation.chat_list.viewModel.ChatListViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.ui.theme.MessageDraw
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.name
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost
import ru.tech.cookhelper.presentation.ui.utils.provider.currentScreen
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate
import ru.tech.cookhelper.presentation.ui.utils.rememberForeverLazyListState
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChatListScreen(viewModel: ChatListViewModel = scopedViewModel()) {
    val screenController = LocalScreenController.current
    val chatListState = viewModel.chatListState.value
    val scrollState = rememberForeverLazyListState(Screen.ChatList::class.name)
    var fabExpanded by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(scrollState) {
        var prev = 0
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .collect {
                fabExpanded = it <= prev
                prev = it
            }
    }

    Box {
        AnimatedContent(targetState = chatListState, modifier = Modifier.fillMaxSize()) { state ->
            if (state.chatList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = scrollState,
                    contentPadding = PaddingValues(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding() + 80.dp
                    )
                ) {
                    itemsIndexed(state.chatList) { index, chat ->
                        ChatListItem(
                            onClick = {
                                screenController.apply {
                                    navigate(
                                        Screen.Chat(
                                            chatId = chat.id,
                                            previousScreen = currentScreen
                                        )
                                    )
                                }
                            },
                            image = chat.image,
                            title = chat.title,
                            lastMessageText = chat.lastMessageText,
                            lastMessageTimestamp = chat.lastMessageTimestamp,
                            newMessagesCount = chat.newMessagesCount
                        )
                        if (index != state.chatList.lastIndex) Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                    }
                }
            }
            else if (!state.isLoading && state.error.isEmpty()) {
                Placeholder(
                    icon = Icons.Filled.MessageDraw,
                    text = stringResource(R.string.no_existing_chats)
                )
            } else if(state.error.isEmpty()){
                Loading()
            } else {
                LocalToastHost.current.sendToast(Icons.Rounded.ErrorOutline, state.error.asString())
            }
        }

        ExpandableFloatingActionButton(
            onClick = { /*TODO*/ },
            expanded = fabExpanded,
            icon = {
                Icon(Icons.Rounded.Edit, null)
            },
            text = { Text(stringResource(R.string.new_chat)) },
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .align(Alignment.BottomEnd)
        )

    }

}