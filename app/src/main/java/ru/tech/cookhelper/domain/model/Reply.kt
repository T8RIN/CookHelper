package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class Reply(
    val author: User,
    val timestamp: Long,
    val attachments: List<FileData>,
    val text: String,
    val id: Long,
    val replyToId: Long,
    val ratingPositive: List<Long>,
    val ratingNegative: List<Long>,
    val replies: List<Reply>
) : Domain

fun Reply.userRate(author: User): Int = when {
    ratingNegative.contains(author.id) -> -1
    ratingPositive.contains(author.id) -> 1
    else -> 0
}

fun Reply.getRating(): String {
    val rating = ratingPositive.size - ratingNegative.size
    return if (rating > 0) "+$rating"
    else "$rating"
}