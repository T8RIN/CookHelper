package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.model.User

data class MessageDto(
    val id: Long,
    val text: String,
    val attachments: List<FileData>,
    val replyToId: Long,
    val views: List<Long>,
    val timestamp: Long,
    val author: User
) : Dto {
    override fun asDomain(): Message = Message(
        id = id,
        text = text,
        attachments = attachments,
        replyToId = replyToId,
        timestamp = timestamp,
        author = author
    )
}