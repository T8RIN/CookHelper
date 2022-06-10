package ru.tech.cookhelper.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.data.remote.api.auth.AuthInfo

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

    fun requestCode(): Flow<Action<*>>

    fun checkCode(code: String): Flow<Action<Boolean>>


}