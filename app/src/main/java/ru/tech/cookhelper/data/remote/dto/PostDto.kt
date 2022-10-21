package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.Image
import ru.tech.cookhelper.domain.model.Post

data class PostDto(
    val id: String,
    val authorId: Long,
    val timestamp: Long,
    val label: String?,
    val text: String?,
    val likes: List<Long>,
    val comments: List<String>,
    val reposts: List<Long>,
    val attachments: List<String>,
    val images: List<Image>,
) : Dto {
    override fun asDomain(): Post = Post(
        id = id,
        authorId = authorId,
        timestamp = timestamp,
        label = label ?: "",
        text = text ?: "",
        likes = likes,
        comments = comments,
        reposts = reposts,
        attachments = attachments,
        images = images
    )
}