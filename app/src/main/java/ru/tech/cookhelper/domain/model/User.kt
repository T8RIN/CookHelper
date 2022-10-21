package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class User(
    val id: Long,
    val avatar: List<String>? = null,
    val bannedIngredients: List<String>? = null,
    val bannedRecipes: List<String>? = null,
    val email: String,
    val forums: List<String>? = null,
    val fridge: List<String>? = null,
    val name: String,
    val nickname: String,
    val starredIngredients: List<String>? = null,
    val userPosts: List<String>? = null,
    val userRecipes: List<String>? = null,
    val starredRecipes: List<String>? = null,
    val status: String? = null,
    val verified: Boolean,
    val surname: String,
    val lastSeen: Long,
    val token: String
) : Domain