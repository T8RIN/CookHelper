package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class Post(
    val id: String,
    val authorId: Long,
    val timestamp: Long,
    val label: String,
    val text: String,
    val likes: List<Long>,
    val comments: List<String>,
    val reposts: List<Long>,
    val attachments: List<String>,
    val images: List<Image>
) : Domain
