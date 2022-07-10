package ru.tech.cookhelper.domain.model

data class Post(
    val postId: String,
    val authorId: String,
    val timestamp: Long,
    val title: String?,
    val text: String?,
    val liked: Boolean,
    val likeCount: Int,
    val commentsCount: Int,
    val image: Image?
)
