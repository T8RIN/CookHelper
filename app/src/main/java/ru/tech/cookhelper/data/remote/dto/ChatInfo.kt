package ru.tech.cookhelper.data.remote.dto

data class ChatInfo(
    val chat: List<MessageDto>,
    val message: String,
    val status: Int
)