package ru.tech.cookhelper.domain.model

data class User(
    val avatar: String?,
    val bannedIngredients: String?,
    val bannedRecipes: String?,
    val email: String,
    val forums: String?,
    val fridge: String?,
    val name: String,
    val nickname: String,
    val ownRecipes: List<Any>?,
    val starredIngredients: String?,
    val starredRecipes: String?,
    val status: String?,
    val verified: Boolean,
    val surname: String,
    val lastSeen: Long,
    val token: String
)