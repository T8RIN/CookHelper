package ru.tech.cookhelper.presentation.chat.components

data class ChatState(
    val isLoading: Boolean = false,
    val image: String? = null,
    val title: String = ""
)
