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

    suspend fun requestPasswordRestoreCode(login: String): Result<AuthInfo>

    fun restorePasswordBy(login: String, code: String, newPassword: String): Flow<Action<AuthInfo>>

    suspend fun requestCode(token: String): Result<AuthInfo>

    fun checkCode(code: String, token: String): Flow<Action<AuthInfo>>

    suspend fun cacheUser(user: User)

    fun getUser(): Flow<User?>

}