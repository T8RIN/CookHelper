package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class Topic(
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
) : Domain