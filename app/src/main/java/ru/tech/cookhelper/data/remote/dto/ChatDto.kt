package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.Chat
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.domain.model.Message

data class ChatDto(
    val id: Long,
    val images: List<FileData>?,
    val title: String,
    val lastMessage: Message,
    val newMessagesCount: Int,
    val members: List<Long>,
    val messages: List<Message>,
    val creationTimestamp: Long
) : Dto {
    override fun asDomain(): Chat = Chat(
        id = id,
        images = images,
        title = title,
        lastMessage = lastMessage,
        newMessagesCount = newMessagesCount,
        members = members,
        messages = messages,
        creationTimestamp = creationTimestamp
    )
}