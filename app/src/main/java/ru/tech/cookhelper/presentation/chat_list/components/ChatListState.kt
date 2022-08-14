package ru.tech.cookhelper.presentation.chat_list.components

import ru.tech.cookhelper.domain.model.Chat
import ru.tech.cookhelper.presentation.ui.utils.UIText

data class ChatListState(
    val isLoading: Boolean = false,
    val chatList: List<Chat> = emptyList(),
    val error: UIText = UIText.empty()
)
