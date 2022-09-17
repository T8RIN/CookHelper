package ru.tech.cookhelper.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.User

interface UserRepository {

    fun loginWith(login: String, password: String): Flow<Action<User?>>

    fun registerWith(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Action<User?>>

    suspend fun requestPasswordRestoreCode(login: String): Action<User?>

    fun restorePasswordBy(login: String, code: String, newPassword: String): Flow<Action<User?>>

    suspend fun requestCode(token: String): Result<User?>

    fun checkCode(code: String, token: String): Flow<Action<User?>>

    suspend fun cacheUser(user: User)

    fun getUser(): Flow<User?>

    suspend fun checkLoginForAvailability(login: String): Action<User?>

    suspend fun checkEmailForAvailability(email: String): Action<User?>

    suspend fun logOut()

}