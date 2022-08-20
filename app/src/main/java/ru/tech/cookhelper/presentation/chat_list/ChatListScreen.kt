package ru.tech.cookhelper.presentation.chat_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import dev.olshevski.navigation.reimagined.navigate
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Loading
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.chat_list.components.ChatListItem
import ru.tech.cookhelper.presentation.chat_list.viewModel.ChatListViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.recipe_post_creation.components.observeExpansion
import ru.tech.cookhelper.presentation.ui.theme.MessageDraw
import ru.tech.cookhelper.presentation.ui.utils.compose.PaddingUtils.addPadding
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChatListScreen(viewModel: ChatListViewModel = hiltViewModel()) {
    val screenController = LocalScreenController.current
    val chatListState = viewModel.chatListState
    val lazyListState = rememberLazyListState()
    var fabExpanded by rememberSaveable { mutableStateOf(true) }

    observeExpansion(lazyListState = lazyListState) { fabExpanded = it }

    Box {
        AnimatedContent(
            targetState = chatListState,
            modifier = Modifier.fillMaxSize()
        ) { state ->
            if (state.chatList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    contentPadding = WindowInsets.navigationBars.asPaddingValues()
                        .addPadding(bottom = 80.dp)
                ) {
                    itemsIndexed(state.chatList) { index, chat ->
                        ChatListItem(
                            onClick = {
                                screenController.navigate(Screen.Chat(chatId = chat.id))
                            },
                            image = chat.image,
                            title = chat.title,
                            lastMessageText = chat.lastMessageText,
                            lastMessageTimestamp = chat.lastMessageTimestamp,
                            newMessagesCount = chat.newMessagesCount
                        )
                        if (index != state.chatList.lastIndex) Separator()
                    }
                }
            } else if (!state.isLoading) {
                Placeholder(
                    icon = Icons.Filled.MessageDraw,
                    text = stringResource(R.string.no_existing_chats)
                )
            } else {
                Loading()
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

    val toastHost = LocalToastHost.current
    val context = LocalContext.current
    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.sendToast(
                Icons.Rounded.ErrorOutline,
                it.text.asString(context)
            )
            else -> {}
        }
    }

}