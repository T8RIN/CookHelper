package ru.tech.cookhelper.domain.model

data class User(
    val id: Long,
    val avatar: String? = null,
    val bannedIngredients: String? = null,
    val bannedRecipes: String? = null,
    val email: String,
    val forums: String? = null,
    val fridge: String? = null,
    val name: String,
    val nickname: String,
    val ownRecipes: String? = null,
    val starredIngredients: String? = null,
    val starredRecipes: String? = null,
    val status: String? = null,
    val verified: Boolean,
    val surname: String,
    val lastSeen: Long,
    val token: String
)