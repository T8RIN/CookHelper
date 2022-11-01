package ru.tech.cookhelper.domain.model

data class FormMessage(
    val text: String,
    val replyToId: Int,
    val attachments: List<FileData>
)