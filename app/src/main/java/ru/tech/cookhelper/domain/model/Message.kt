package ru.tech.cookhelper.domain.model

data class Message(
    val id: Long,
    val timestamp: Long,
    val text: String,
    val attachmentId: String?,
    val userId: Long
)
