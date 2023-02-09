package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class User(
    val id: Long,
    val avatar: List<FileData> = emptyList(),
    val bannedIngredients: List<String>? = null,
    val bannedRecipes: List<String>? = null,
    val email: String,
    val forums: List<String>? = null,
    val fridge: List<Product> = emptyList(),
    val name: String,
    val nickname: String,
    val starredIngredients: List<String>? = null,
    val userPosts: List<String>? = null,
    val userRecipes: List<String>? = null,
    val starredRecipes: List<String>? = null,
    val status: String? = "",
    val verified: Boolean = false,
    val surname: String,
    val lastSeen: Long = 0,
    val token: String = ""
) : Domain

fun User?.getLastAvatar(): String? = this?.avatar?.lastOrNull()?.link