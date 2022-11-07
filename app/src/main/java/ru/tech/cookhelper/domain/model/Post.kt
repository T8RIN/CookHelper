package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class Post(
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
) : Domain
