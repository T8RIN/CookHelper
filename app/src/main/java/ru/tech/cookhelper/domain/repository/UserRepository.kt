package ru.tech.cookhelper.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Post
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.domain.model.Topic
import ru.tech.cookhelper.domain.model.User
import java.io.File

interface UserRepository {

    fun loginWith(login: String, password: String): Flow<Action<User>>

    fun registerWith(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Action<User>>

    suspend fun requestPasswordRestoreCode(login: String): Action<User?>

    fun restorePasswordBy(
        login: String,
        code: String,
        newPassword: String
    ): Flow<Action<User>>

    suspend fun requestCode(token: String): Result<User?>

    fun checkCode(code: String, token: String): Flow<Action<User>>

    suspend fun cacheUser(user: User)

    fun getUser(): Flow<User?>

    suspend fun checkLoginForAvailability(login: String): Action<Boolean>

    suspend fun checkEmailForAvailability(email: String): Action<Boolean>

    suspend fun logOut()

    suspend fun loadUserById(id: String): User?

    fun getFeed(token: String): Flow<Action<List<Recipe>>>

    fun stopAwaitingFeed()

    fun createPost(
        token: String,
        label: String,
        content: String,
        imageFile: File?,
        type: String
    ): Flow<Action<Post>>

    fun createTopic(
        token: String,
        title: String,
        text: String,
        attachments: List<Pair<File?, String>>,
        tags: List<String>
    ): Flow<Action<Topic>>

    fun observeUser(
        id: Long,
        token: String
    ): Flow<Action<User>>

}