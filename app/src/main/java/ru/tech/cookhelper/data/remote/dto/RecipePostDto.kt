package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.domain.model.RecipePost
import ru.tech.cookhelper.domain.model.User

class RecipePostDto(
    val postId: String,
    val authorId: String,
    val timestamp: Long,
    val liked: Boolean,
    val likeCount: Int,
    val commentsCount: Int,
    val recipe: Recipe,
    val author: User
) : Dto {

    override fun asDomain(): RecipePost = RecipePost(
        postId,
        authorId,
        timestamp,
        liked,
        likeCount,
        commentsCount,
        recipe,
        author
    )

}