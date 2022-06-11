package ru.tech.cookhelper.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.data.remote.api.auth.AuthInfo
import ru.tech.cookhelper.domain.model.User

interface UserRepository {

    fun loginWith(login: String, password: String): Flow<Action<AuthInfo>>

    fun registerWith(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Action<AuthInfo>>

    fun restorePasswordFor(email: String): Flow<Action<*>>

    suspend fun requestCode(token: String): AuthInfo

    fun checkCode(code: String, token: String): Flow<Action<AuthInfo>>

    suspend fun cacheUser(user: User)

    fun getUser(): Flow<User?>

}