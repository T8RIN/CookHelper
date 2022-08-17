package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class Message(
    val id: Long,
    val timestamp: Long,
    val text: String,
    val attachmentId: String?,
    val userId: Long
) : Domain
