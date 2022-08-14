package ru.tech.cookhelper.presentation.chat_list.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.domain.model.Chat
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    val chatList: State<List<Chat>> = mutableStateOf(
        listOf(
            Chat(id = "1", image = "https://i.stack.imgur.com/CwtL5.png", title = "как какать", lastMessageText = "Никак", lastMessageTimestamp = System.currentTimeMillis(), newMessagesCount = 1),
            Chat(id = "2", image = null, title = "CockHelper", lastMessageText = "such a nice app", lastMessageTimestamp = System.currentTimeMillis(), newMessagesCount = 0)
        )
    )

    init {
        getUserUseCase().onEach {
            _user.value = it
        }.launchIn(viewModelScope)
    }


}