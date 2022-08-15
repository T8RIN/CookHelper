package ru.tech.cookhelper.presentation.chat.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.await_new_messages.AwaitNewMessagesUseCase
import ru.tech.cookhelper.domain.use_case.get_all_messages.GetAllMessagesUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.send_message.SendMessagesUseCase
import ru.tech.cookhelper.domain.use_case.stop_awaiting_messages.StopAwaitingMessagesUseCase
import ru.tech.cookhelper.presentation.chat.components.ChatState
import ru.tech.cookhelper.presentation.ui.utils.UIText
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val stopAwaitingMessagesUseCase: StopAwaitingMessagesUseCase,
    private val sendMessagesUseCase: SendMessagesUseCase,
    private val getAllMessagesUseCase: GetAllMessagesUseCase,
    private val awaitNewMessagesUseCase: AwaitNewMessagesUseCase,
    getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var chatId: String = ""

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    private val _chatState: MutableState<ChatState> = mutableStateOf(ChatState())
    val chatState: State<ChatState> = _chatState

    val messages = mutableStateListOf<Message>()

    init {
        getUserUseCase().onEach {
            _user.value = it?.copy(id = 1)
        }.launchIn(viewModelScope)
        chatId = savedStateHandle["chatId"]!!
        awaitAndGetMessages(chatId)
    }

    private fun awaitAndGetMessages(chatId: String) {
        awaitNewMessagesUseCase(chatId = chatId, token = "qwe")
            .onEach { action ->
                when (action) {
                    is Action.Empty -> _chatState.value = ChatState()
                    is Action.Error -> _chatState.value = ChatState(
                        errorMessage = if (!(action.message
                                ?: "").contains("Unable to resolve host")
                        ) UIText.DynamicString(action.message ?: "") else UIText.empty()
                    )
                    is Action.Loading -> _chatState.value = ChatState(isLoading = true)
                    is Action.Success -> action.data?.let { messages.add(it) }.also {
                        _chatState.value = ChatState()
                    }
                }
            }.launchIn(viewModelScope)

        getAllMessagesUseCase(chatId = chatId, token = "qwe")
            .onEach { action ->
                when (action) {
                    is Action.Empty -> TODO()
                    is Action.Error -> TODO()
                    is Action.Loading -> TODO()
                    is Action.Success -> action.data?.let { messages.addAll(it) }
                }
            }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        stopAwaitingMessagesUseCase()
    }

    fun resetState() {
        _chatState.value = ChatState()
    }

    fun send(message: String) {
        sendMessagesUseCase(message)
    }

}