package ru.tech.cookhelper.data.repository

import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.data.local.dao.UserDao
import ru.tech.cookhelper.data.local.entity.asDatabaseEntity
import ru.tech.cookhelper.data.remote.api.auth.AuthService
import ru.tech.cookhelper.data.remote.api.user.UserApi
import ru.tech.cookhelper.data.remote.dto.RecipePostDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.remote.web_socket.feed.FeedService
import ru.tech.cookhelper.data.utils.JsonParser
import ru.tech.cookhelper.domain.model.RecipePost
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val feedService: FeedService,
    private val jsonParser: JsonParser
) : UserRepository {

    override fun loginWith(login: String, password: String): Flow<Action<User?>> = flow {
        emit(Action.Loading())
        val response = io { authService.loginWith(login, password).execute() }
        val body = response.let { it.body() ?: throw Exception("${it.code()} ${it.message()}") }

        when (body.status) {
            101, 102 -> emit(Action.Empty(body.status))
            100, 103 -> emit(Action.Success(data = body.data?.asDomain()))
            else -> emit(Action.Error(message = body.message))
        }
    }.catch { t -> emit(Action.Error(message = t.message.toString())) }

    override fun registerWith(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Action<User?>> = flow {
        emit(Action.Loading())
        val response =
            io { authService.registerWith(name, surname, nickname, email, password).execute() }
        val body = response.let { it.body() ?: throw Exception("${it.code()} ${it.message()}") }

        if (body.status == 100) emit(Action.Success(data = body.data?.asDomain()))
        else emit(Action.Error(message = body.message))

    }.catch { t -> emit(Action.Error(message = t.message.toString())) }

    override suspend fun requestCode(
        token: String
    ): Result<User?> = runCatching {
        authService.requestCode(token).data?.asDomain()
    }

    override fun checkCode(code: String, token: String): Flow<Action<User?>> = flow {
        emit(Action.Loading())
        val response = io { authService.verifyEmail(code, token).execute() }
        val body = response.let { it.body() ?: throw Exception("${it.code()} ${it.message()}") }

        when (body.status) {
            102 -> emit(Action.Empty())
            100 -> emit(Action.Success(data = body.data?.asDomain()))
            else -> emit(Action.Error(message = body.message))
        }
    }.catch { t -> emit(Action.Error(message = t.message.toString())) }

    override suspend fun cacheUser(user: User) = userDao.cacheUser(user.asDatabaseEntity())

    override fun getUser(): Flow<User?> = userDao.getUser().map { it?.asDomain() }

    override suspend fun checkLoginForAvailability(
        login: String
    ): Action<User?> = try {
        val response = authService.checkLoginOrEmailForAvailability(login)
        if (response.status == 100) Action.Success(data = response.data?.asDomain())
        else Action.Error(message = response.message)
    } catch (t: Throwable) {
        Action.Error(message = t.message.toString())
    }

    override suspend fun checkEmailForAvailability(
        email: String
    ): Action<User?> = try {
        val response = authService.checkLoginOrEmailForAvailability(email)
        if (response.status == 100) Action.Success(data = response.data?.asDomain())
        else Action.Error(message = response.message)
    } catch (t: Throwable) {
        Action.Error(message = t.message.toString())
    }

    override suspend fun logOut() = userDao.clearUser()

    override suspend fun loadUserById(id: String): User? {
        TODO()
    }

    override fun getFeed(token: String): Flow<Action<List<RecipePost>>> = flow {
        feedService(token = token)
            .catch { emit(Action.Error(message = it.message)) }
            .collect { state ->
                when (state) {
                    is WebSocketState.Error -> emit(Action.Error(message = state.message))
                    is WebSocketState.Message -> jsonParser.fromJson<List<RecipePostDto>>(
                        json = state.text,
                        type = Types.newParameterizedType(
                            List::class.java,
                            RecipePostDto::class.java
                        )
                    )?.let { it -> emit(Action.Success(data = it.map { it.asDomain() })) }
                    WebSocketState.Closing -> emit(Action.Loading())
                    is WebSocketState.Opened -> {
                        jsonParser.fromJson<List<RecipePostDto>>(
                            json = state.response.body.string(),
                            type = Types.newParameterizedType(
                                List::class.java,
                                RecipePostDto::class.java
                            )
                        )?.let { it -> emit(Action.Success(data = it.map { it.asDomain() })) }
                    }
                    WebSocketState.Opening -> emit(Action.Loading())
                    WebSocketState.Restarting -> emit(Action.Loading())
                    else -> {}
                }
            }
        emit(Action.Loading())
        val response = userApi.getFeed(token)
        when {
            response.isFailure -> {
                emit(Action.Error(response.exceptionOrNull()?.message ?: ""))
            }
            response.isSuccess -> {
                val result = response.getOrNull()
                if (result?.status == 100) emit(Action.Success(data = result.data?.map { it.asDomain() }
                    ?: emptyList()))
                else emit(Action.Empty(result?.status))
            }
        }
    }

    override fun stopAwaitingFeed() = feedService.close()

    override suspend fun requestPasswordRestoreCode(
        login: String
    ): Action<User?> {
        val result = kotlin.runCatching { authService.requestPasswordRestoreCode(login) }
        if (result.isFailure) {
            return Action.Error(message = result.exceptionOrNull()?.message)
        } else {
            val authInfo = result.getOrNull()
            if (authInfo != null) {
                return when (authInfo.status) {
                    100 -> Action.Success(authInfo.data?.asDomain())
                    else -> {
                        Action.Empty(status = authInfo.status)
                    }
                }
            }
            return Action.Empty()
        }
    }


    override fun restorePasswordBy(
        login: String,
        code: String,
        newPassword: String
    ): Flow<Action<User?>> = flow {
        emit(Action.Loading())
        val response = io { authService.restorePasswordBy(login, code, newPassword).execute() }
        val body = response.let { it.body() ?: throw Exception("${it.code()} ${it.message()}") }

        when (body.status) {
            102 -> emit(Action.Empty())
            100 -> emit(Action.Success(data = body.data?.asDomain()))
            else -> emit(Action.Error(message = body.message))
        }
    }.catch { t -> emit(Action.Error(message = t.message.toString())) }

}

private suspend fun <T> io(
    function: suspend () -> T
): T = withContext(Dispatchers.IO) { function() }