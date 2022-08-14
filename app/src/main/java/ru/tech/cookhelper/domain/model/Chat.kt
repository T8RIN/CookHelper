package ru.tech.cookhelper.domain.model

data class Chat(
    val id: String,
    val image: String?,
    val title: String,
    val lastMessageText: String,
    val lastMessageTimestamp: Long,
    val newMessagesCount: Int
)