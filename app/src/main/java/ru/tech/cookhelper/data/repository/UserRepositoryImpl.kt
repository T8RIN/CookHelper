package ru.tech.cookhelper.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.Response
import retrofit2.Call
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.data.remote.api.auth.AuthInfo
import ru.tech.cookhelper.data.remote.api.auth.AuthService
import ru.tech.cookhelper.data.remote.body.LoginBody
import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : UserRepository {

    @Suppress("BlockingMethodInNonBlockingContext")
    override fun loginWith(login: String, password: String): Flow<Action<AuthInfo>> = flow {
        emit(Action.Loading())
        try {
            val response = withContext(Dispatchers.IO) { authService.loginWith(LoginBody(login, password)).execute() }

            if (response.body()?.status == -1) emit(Action.Empty())
            else response.body()?.let { emit(Action.Success(data = it)) }
        } catch (t: Throwable) {
            emit(Action.Error(message = t.message.toString()))
        }
    }

    override fun registerWith(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Action<AuthInfo>> {
        TODO("Not yet implemented")
    }

    override fun restorePasswordFor(email: String): Flow<Action<*>> {
        TODO("Not yet implemented")
    }

    override fun requestCode(): Flow<Action<*>> {
        TODO("Not yet implemented")
    }

    override fun checkCode(code: String): Flow<Action<Boolean>> {
        TODO("Not yet implemented")
    }

}