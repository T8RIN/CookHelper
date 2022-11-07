package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.domain.model.Post
import ru.tech.cookhelper.domain.model.User

data class PostDto(
    val id: Long,
    val author: User,
    val timestamp: Long,
    val label: String,
    val text: String,
    val likes: List<Long>,
    val comments: List<String>,
    val reposts: List<Long>,
    val attachments: List<FileData>,
    val images: List<FileData>
) : Dto {
    override fun asDomain(): Post = Post(
        id = id,
        author = author,
        timestamp = timestamp,
        label = label,
        text = text,
        likes = likes,
        comments = comments,
        reposts = reposts,
        attachments = attachments,
        images = attachments
    )
}