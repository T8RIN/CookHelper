package ru.tech.cookhelper.presentation.chat.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.onEmpty
import ru.tech.cookhelper.core.onError
import ru.tech.cookhelper.core.onLoading
import ru.tech.cookhelper.core.onSuccess
import ru.tech.cookhelper.domain.model.FormMessage
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.await_new_messages.AwaitNewMessagesUseCase
import ru.tech.cookhelper.domain.use_case.get_all_messages.GetChatUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.send_message.SendMessagesUseCase
import ru.tech.cookhelper.domain.use_case.stop_awaiting_messages.StopAwaitingMessagesUseCase
import ru.tech.cookhelper.presentation.chat.components.ChatState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.getUIText
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val stopAwaitingMessagesUseCase: StopAwaitingMessagesUseCase,
    private val sendMessagesUseCase: SendMessagesUseCase,
    private val getChatUseCase: GetChatUseCase,
    private val awaitNewMessagesUseCase: AwaitNewMessagesUseCase,
    getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private var chatId: Long = 0

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: User? by _user

    private val _chatState: MutableState<ChatState> = mutableStateOf(
        ChatState(
            image = savedStateHandle["image"],
            title = savedStateHandle["title"]!!
        )
    )
    val chatState: ChatState by _chatState

    private val _loadingAllMessages: MutableState<Boolean> = mutableStateOf(false)
    val loadingAllMessages: Boolean by _loadingAllMessages

    val messages = mutableStateListOf<Message>()

    init {
        getUserUseCase().onEach {
            it?.let {
                _user.update { it }
                chatId = savedStateHandle["chatId"]!!
                awaitAndGetMessages(chatId, it.token)
            }
        }.launchIn(viewModelScope)
    }

    private fun awaitAndGetMessages(chatId: Long, token: String) {

        getChatUseCase(
            chatId = chatId,
            token = token
        ).onEmpty {
            this?.getUIText()?.let {
                sendEvent(Event.ShowToast(it))
            }
            _loadingAllMessages.value = false
        }.onError {
            sendEvent(Event.ShowToast(UIText(this)))
            _loadingAllMessages.value = false
        }.onLoading {
            _loadingAllMessages.value = true
        }.onSuccess {
            this@ChatViewModel.messages.addAll(this@onSuccess.messages)
            _loadingAllMessages.value = false
        }.launchIn(viewModelScope)

        awaitNewMessagesUseCase(
            chatId = chatId,
            token = token
        ).onEmpty {
            _chatState.update { copy(isLoading = false) }
        }.onError {
            _chatState.update { copy(isLoading = false) }
            sendEvent(Event.ShowToast(UIText(this)))
        }.onLoading {
            _chatState.update { copy(isLoading = true) }
        }.onSuccess {
            messages.add(this).also {
                _chatState.update { copy(isLoading = false) }
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        stopAwaitingMessagesUseCase()
    }

    fun send(message: String) {
        sendMessagesUseCase(FormMessage(message, 0, emptyList()))
    }

}