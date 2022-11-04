package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.domain.model.Reply
import ru.tech.cookhelper.domain.model.Topic
import ru.tech.cookhelper.domain.model.User

data class TopicDto(
    val id: Long,
    val author: User,
    val title: String,
    val text: String,
    val replies: List<Reply>,
    val attachments: List<FileData>,
    val tags: List<String>,
    val timestamp: Long,
    val closed: Boolean,
    val ratingPositive: List<Long>,
    val ratingNegative: List<Long>
) : Dto {
    override fun asDomain(): Topic = Topic(
        id = id,
        author = author,
        title = title,
        text = text,
        replies = replies,
        attachments = attachments,
        tags = tags,
        timestamp = timestamp,
        closed = closed,
        ratingPositive = ratingPositive,
        ratingNegative = ratingNegative
    )
}
