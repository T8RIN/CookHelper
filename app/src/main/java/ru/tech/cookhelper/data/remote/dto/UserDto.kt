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
    val ownRecipes: List<String>?,
    val starredIngredients: List<String>?,
    val starredRecipes: List<String>?,
    val status: String?,
    val verified: Boolean?,
    val surname: String?,
    val lastSeen: Long?,
    val token: String?
) : Dto {
    override fun asDomain(): User = User(
        id ?: 0,
        avatar,
        bannedIngredients,
        bannedRecipes,
        email ?: "",
        forums,
        fridge,
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
}