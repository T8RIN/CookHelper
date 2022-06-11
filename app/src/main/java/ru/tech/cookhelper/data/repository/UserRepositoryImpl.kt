package ru.tech.cookhelper.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.data.local.dao.UserDao
import ru.tech.cookhelper.data.local.entity.toEntity
import ru.tech.cookhelper.data.local.entity.toUser
import ru.tech.cookhelper.data.remote.api.auth.AuthInfo
import ru.tech.cookhelper.data.remote.api.auth.AuthService
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class UserRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val userDao: UserDao
) : UserRepository {

    override fun loginWith(login: String, password: String): Flow<Action<AuthInfo>> = flow {
        emit(Action.Loading())
        val response = withContext(Dispatchers.IO) {
            authService.loginWith(login, password).execute()
        }
        val body = response.let { it.body() ?: throw Exception("${it.code()} ${it.message()}") }

        if (body.status == -1) emit(Action.Empty())
        else emit(Action.Success(data = body))
    }.catch { t -> emit(Action.Error(message = t.message.toString())) }

    override fun registerWith(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Action<AuthInfo>> = flow {
        emit(Action.Loading())
        val response = withContext(Dispatchers.IO) {
            authService.registerWith(name, surname, nickname, email, password).execute()
        }
        val body = response.let { it.body() ?: throw Exception("${it.code()} ${it.message()}") }

        if (body.status == -1) emit(Action.Error(message = body.message))
        else emit(Action.Success(data = body))
    }.catch { t -> emit(Action.Error(message = t.message.toString())) }

    override fun restorePasswordFor(email: String): Flow<Action<*>> {
        TODO("Not yet implemented")
    }

    override suspend fun requestCode(token: String): AuthInfo = authService.requestCode(token)

    override fun checkCode(code: String, token: String): Flow<Action<AuthInfo>> = flow {
        emit(Action.Loading())
        val response = withContext(Dispatchers.IO) {
            authService.verifyEmail(code, token).execute()
        }
        val body = response.let { it.body() ?: throw Exception("${it.code()} ${it.message()}") }

        when (body.status) {
            -1 -> emit(Action.Empty())
            0 -> emit(Action.Error(message = body.message))
            else -> emit(Action.Success(data = body))
        }
    }.catch { t -> emit(Action.Error(message = t.message.toString())) }

    override suspend fun cacheUser(user: User) = userDao.cacheUser(user.toEntity())

    override fun getUser(): Flow<User?> = userDao.getUser().map { it?.toUser() }
}