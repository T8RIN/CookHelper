package ru.tech.cookhelper.presentation.chat_list.components

import ru.tech.cookhelper.domain.model.Chat

data class ChatListState(
    val isLoading: Boolean = false,
    val chatList: List<Chat> = emptyList()
)
