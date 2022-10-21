package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.User

data class UserDto(
    val id: Long?,
    val avatar: List<String>?,
    val bannedIngredients: List<String>?,
    val bannedRecipes: List<String>?,
    val email: String?,
    val forums: List<String>?,
    val fridge: List<String>?,
    val name: String?,
    val nickname: String?,
    val userPosts: List<String>? = null,
    val userRecipes: List<String>? = null,
    val starredIngredients: List<String>?,
    val starredRecipes: List<String>?,
    val status: String?,
    val verified: Boolean?,
    val surname: String?,
    val lastSeen: Long?,
    val token: String?
) : Dto {
    override fun asDomain(): User = User(
        id = id ?: 0,
        avatar = avatar,
        bannedIngredients = bannedIngredients,
        bannedRecipes = bannedRecipes,
        email = email ?: "",
        forums = forums,
        fridge = fridge,
        name = name ?: "",
        nickname = nickname ?: "",
        starredIngredients = userPosts,
        userPosts = userRecipes,
        userRecipes = starredIngredients,
        starredRecipes = starredRecipes,
        status = status,
        verified = verified ?: false,
        surname = surname ?: "",
        lastSeen = lastSeen ?: 0L,
        token = token ?: ""
    )
}