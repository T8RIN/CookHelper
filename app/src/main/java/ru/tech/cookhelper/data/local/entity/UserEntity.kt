package ru.tech.cookhelper.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tech.cookhelper.data.local.utils.DatabaseEntity
import ru.tech.cookhelper.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey val id: Long,
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
) : DatabaseEntity {
    override fun asDomain(): User = User(
        id,
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
}

fun User.asDatabaseEntity() = UserEntity(
    id,
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