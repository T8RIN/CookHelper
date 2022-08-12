package ru.tech.cookhelper.presentation.chat.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.await_new_messages.AwaitNewMessagesUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.send_message.SendMessagesUseCase
import ru.tech.cookhelper.domain.use_case.stop_awaiting_messages.StopAwaitingMessagesUseCase
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val stopAwaitingMessagesUseCase: StopAwaitingMessagesUseCase,
    private val sendMessagesUseCase: SendMessagesUseCase,
    awaitNewMessagesUseCase: AwaitNewMessagesUseCase,
    getUserUseCase: GetUserUseCase
) : ViewModel() {

    fun send(message: String) {
        sendMessagesUseCase(message)
    }

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    val messages = mutableStateListOf<Message>()

    init {
        getUserUseCase().onEach {
            _user.value = it
        }.launchIn(viewModelScope)

        awaitNewMessagesUseCase(chatId = "1", token = user.value?.token ?: "")
            .onEach { action ->
                when (action) {
                    is Action.Empty -> TODO()
                    is Action.Error -> {}
                    is Action.Loading -> TODO()
                    is Action.Success -> action.data?.let { messages.add(it) }
                }
            }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        stopAwaitingMessagesUseCase()
    }

}