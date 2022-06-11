package ru.tech.cookhelper.data.remote.api.auth

data class User(
    val avatar: String,
    val bannedIngredients: String,
    val bannedRecipes: String,
    val email: String,
    val forums: String,
    val fridge: String,
    val name: String,
    val nickname: String,
    val ownRecipes: List<Any>,
    val starredIngredients: String,
    val starredRecipes: String,
    val status: Int,
    val verified: Boolean,
    val surname: String,
    val token: String
)