package ru.tech.cookhelper.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tech.cookhelper.data.local.utils.DatabaseEntity
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey val id: Long,
    val avatar: List<FileData>,
    val bannedIngredients: List<String>,
    val bannedRecipes: List<String>,
    val email: String,
    val forums: List<String>,
    val fridge: List<String>,
    val name: String,
    val nickname: String,
    val userPosts: List<String>? = null,
    val userRecipes: List<String>? = null,
    val starredIngredients: List<String>,
    val starredRecipes: List<String>,
    val status: String?,
    val verified: Boolean,
    val surname: String,
    val lastSeen: Long,
    val token: String
) : DatabaseEntity {
    override fun asDomain(): User = User(
        id = id,
        avatar = avatar,
        bannedIngredients = bannedIngredients,
        bannedRecipes = bannedRecipes,
        email = email,
        forums = forums,
        fridge = fridge,
        name = name,
        nickname = nickname,
        starredIngredients = userPosts,
        userPosts = userRecipes,
        userRecipes = starredIngredients,
        starredRecipes = starredRecipes,
        status = status,
        verified = verified,
        surname = surname,
        lastSeen = lastSeen,
        token = token
    )
}

fun User.asDatabaseEntity() = UserEntity(
    id = id,
    avatar = avatar,
    bannedIngredients = bannedIngredients ?: emptyList(),
    bannedRecipes = bannedRecipes ?: emptyList(),
    email = email,
    forums = forums ?: emptyList(),
    fridge = fridge ?: emptyList(),
    name = name,
    nickname = nickname,
    userPosts = userPosts ?: emptyList(),
    userRecipes = userRecipes ?: emptyList(),
    starredIngredients = starredIngredients ?: emptyList(),
    starredRecipes = starredRecipes ?: emptyList(),
    status = status,
    verified = verified,
    surname = surname,
    lastSeen = lastSeen,
    token = token
)