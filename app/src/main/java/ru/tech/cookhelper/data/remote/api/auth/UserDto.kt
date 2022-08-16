package ru.tech.cookhelper.data.remote.api.auth

import ru.tech.cookhelper.domain.model.User

data class UserDto(
    val id: Long?,
    val avatar: String?,
    val bannedIngredients: String?,
    val bannedRecipes: String?,
    val email: String?,
    val forums: String?,
    val fridge: List<String>?,
    val name: String?,
    val nickname: String?,
    val ownRecipes: String?,
    val starredIngredients: String?,
    val starredRecipes: String?,
    val status: String?,
    val verified: Boolean?,
    val surname: String?,
    val lastSeen: Long?,
    val token: String?
)

fun UserDto.toUser() = User(
    id ?: 0,
    avatar,
    bannedIngredients,
    bannedRecipes,
    email ?: "",
    forums,
    fridge?.joinToString(),
    name ?: "",
    nickname ?: "",
    ownRecipes,
    starredIngredients,
    starredRecipes,
    status,
    verified ?: false,
    surname ?: "",
    lastSeen ?: 0L,
    token ?: ""
)