package ru.tech.cookhelper.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tech.cookhelper.data.local.utils.DatabaseEntity
import ru.tech.cookhelper.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey val id: Long,
    val avatar: List<String>,
    val bannedIngredients: List<String>,
    val bannedRecipes: List<String>,
    val email: String,
    val forums: List<String>,
    val fridge: List<String>,
    val name: String,
    val nickname: String,
    val ownRecipes: List<String>,
    val starredIngredients: List<String>,
    val starredRecipes: List<String>,
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
    avatar ?: emptyList(),
    bannedIngredients ?: emptyList(),
    bannedRecipes ?: emptyList(),
    email,
    forums ?: emptyList(),
    fridge ?: emptyList(),
    name,
    nickname,
    ownRecipes ?: emptyList(),
    starredIngredients ?: emptyList(),
    starredRecipes ?: emptyList(),
    status,
    verified,
    surname,
    lastSeen,
    token
)