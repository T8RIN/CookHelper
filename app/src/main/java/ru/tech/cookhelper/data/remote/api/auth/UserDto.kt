package ru.tech.cookhelper.data.remote.api.auth

import ru.tech.cookhelper.domain.model.User

data class UserDto(
    val avatar: String?,
    val bannedIngredients: String?,
    val bannedRecipes: String?,
    val email: String,
    val forums: String?,
    val fridge: String?,
    val name: String,
    val nickname: String,
    val ownRecipes: String?,
    val starredIngredients: String?,
    val starredRecipes: String?,
    val status: String?,
    val verified: Boolean,
    val surname: String,
    val lastSeen: Long,
    val token: String
)

fun UserDto.toUser() = User(
    avatar,
    bannedIngredients,
    bannedRecipes,
    email,
    forums,
    fridge,
    name,
    nickname,
    ownRecipes,
    starredIngredients,
    starredRecipes,
    status,
    verified,
    surname,
    lastSeen,
    token
)