package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class Message(
    val id: Long,
    val text: String,
    val attachments: List<FileData>,
    val replyToId: Long,
    val timestamp: Long,
    val author: User
) : Domain
