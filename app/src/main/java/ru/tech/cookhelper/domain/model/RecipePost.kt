package ru.tech.cookhelper.domain.model

data class RecipePost(
    val postId: String,
    val authorId: String,
    val timestamp: Long,
    val liked: Boolean,
    val likeCount: Int,
    val commentsCount: Int,
    val recipe: Recipe
)

