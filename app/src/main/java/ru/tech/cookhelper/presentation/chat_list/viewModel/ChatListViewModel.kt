package ru.tech.cookhelper.presentation.chat_list.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.cookhelper.domain.model.Chat
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.get_chat_list.GetChatListUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.presentation.chat_list.components.ChatListState
import ru.tech.cookhelper.presentation.ui.utils.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val getChatListUseCase: GetChatListUseCase
) : ViewModel(), ViewModelEvents by ViewModelEventsImpl() {

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    private val _chatListState: MutableState<ChatListState> = mutableStateOf(ChatListState())
    val chatListState: State<ChatListState> = _chatListState

    init {
        getUserUseCase().onEach {
            _user.value = it
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            while (true) {
                delay(3000)
                _chatListState.value = ChatListState(
                    chatList = listOf(
                        Chat(
                            id = "1",
                            image = "https://i.stack.imgur.com/CwtL5.png",
                            title = "Интеллектуалы",
                            lastMessageText = "О чем речь?",
                            lastMessageTimestamp = System.currentTimeMillis(),
                            newMessagesCount = 1
                        ),
                        Chat(
                            id = "2",
                            image = null,
                            title = "CockHelper",
                            lastMessageText = "such a nice app",
                            lastMessageTimestamp = System.currentTimeMillis(),
                            newMessagesCount = 0
                        ),
                        Chat(
                            id = "3",
                            image = null,
                            title = "Артур",
                            lastMessageText = "Хммммм",
                            lastMessageTimestamp = System.currentTimeMillis(),
                            newMessagesCount = 6
                        ),
                        Chat(
                            id = "4",
                            image = null,
                            title = "No_Feelings",
                            lastMessageText = "Я *** и что вы мне сделаете, плюс еще и такое длинное сообщение написал, что * можно 8 раз",
                            lastMessageTimestamp = System.currentTimeMillis(),
                            newMessagesCount = 10022
                        ),
                        Chat(
                            id = "5",
                            image = null,
                            title = "Dino",
                            lastMessageText = "Слыш ты арбуз",
                            lastMessageTimestamp = System.currentTimeMillis(),
                            newMessagesCount = 99
                        )
                    )
                )
                delay(3000)
                _chatListState.value = ChatListState()
                delay(3000)
                _chatListState.value = ChatListState(isLoading = true)
                delay(3000)
                sendEvent(Event.ShowToast(UIText.DynamicString("Трешачок какой-то")))
            }
        }
    }
}