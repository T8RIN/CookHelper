package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class Chat(
    val id: Long,
    val images: List<FileData>?,
    val title: String,
    val lastMessage: Message,
    val newMessagesCount: Int,
    val members: List<Long>,
    val messages: List<Message>,
    val creationTimestamp: Long
) : Domain
