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
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.await_new_messages.AwaitNewMessagesUseCase
import ru.tech.cookhelper.domain.use_case.get_all_messages.GetAllMessagesUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.send_message.SendMessagesUseCase
import ru.tech.cookhelper.domain.use_case.stop_awaiting_messages.StopAwaitingMessagesUseCase
import ru.tech.cookhelper.presentation.chat.components.ChatState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val stopAwaitingMessagesUseCase: StopAwaitingMessagesUseCase,
    private val sendMessagesUseCase: SendMessagesUseCase,
    private val getAllMessagesUseCase: GetAllMessagesUseCase,
    private val awaitNewMessagesUseCase: AwaitNewMessagesUseCase,
    getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private var chatId: String = ""

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
            it?.let { _user.update { it.copy(id = 1) } }
        }.launchIn(viewModelScope)
        chatId = savedStateHandle["chatId"]!!
        awaitAndGetMessages(chatId)
    }

    private fun awaitAndGetMessages(chatId: String) {
        awaitNewMessagesUseCase(
            chatId = chatId,
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcmczIjoxNjY3MjUyNzMwNTE5LCJzdWIiOiJpby5rdG9yLnNlcnZlci5jb25maWcuSG9jb25BcHBsaWNhdGlvbkNvbmZpZyRIb2NvbkFwcGxpY2F0aW9uQ29uZmlnVmFsdWVAMmZmZGIyZCIsImFyZzIiOiJzdXJuYW1lIiwiYXJnNCI6IltCQDRmNTcxMGQ3IiwiYXJnMSI6Im5hbWUiLCJpc3MiOiJpby5rdG9yLnNlcnZlci5jb25maWcuSG9jb25BcHBsaWNhdGlvbkNvbmZpZyRIb2NvbkFwcGxpY2F0aW9uQ29uZmlnVmFsdWVANDIzYzJiNzQiLCJleHAiOjE2NjcyNTc5MTR9.GA70GqcXN9hgI0CqeIPdlQs6AKxKv7uKHxfhV_bMA6Q"
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
        sendMessagesUseCase(message)
    }

}