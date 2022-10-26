package ru.tech.cookhelper.data.repository

import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.core.constants.Status.SUCCESS
import ru.tech.cookhelper.core.constants.Status.USER_NOT_VERIFIED
import ru.tech.cookhelper.core.utils.RetrofitUtils.bodyOrThrow
import ru.tech.cookhelper.core.utils.RetrofitUtils.toMultipartFormData
import ru.tech.cookhelper.core.utils.kotlin.getOrExceptionAndNull
import ru.tech.cookhelper.core.utils.kotlin.runIo
import ru.tech.cookhelper.data.local.dao.UserDao
import ru.tech.cookhelper.data.local.entity.asDatabaseEntity
import ru.tech.cookhelper.data.remote.api.auth.AuthService
import ru.tech.cookhelper.data.remote.api.user.UserApi
import ru.tech.cookhelper.data.remote.dto.RecipePostDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.remote.web_socket.feed.FeedService
import ru.tech.cookhelper.data.utils.JsonParser
import ru.tech.cookhelper.domain.model.Post
import ru.tech.cookhelper.domain.model.RecipePost
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.repository.UserRepository
import ru.tech.cookhelper.presentation.ui.utils.android.ImageUtils.compress
import ru.tech.cookhelper.presentation.ui.utils.android.ImageUtils.isGif
import ru.tech.cookhelper.presentation.ui.utils.android.ImageUtils.isSvg
import ru.tech.cookhelper.presentation.ui.utils.toAction
import java.io.File
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val feedService: FeedService,
    private val jsonParser: JsonParser
) : UserRepository {

    override fun loginWith(login: String, password: String): Flow<Action<User>> = flow {
        emit(Action.Loading())

        val response = runIo { authService.loginWith(login, password).execute() }
        val body = response.bodyOrThrow()

        when (body.status) {
            SUCCESS, USER_NOT_VERIFIED -> emit(Action.Success(data = body.data?.asDomain()))
            else -> emit(Action.Empty(body.status))
        }
    }.catch { emit(it.toAction()) }

    override fun registerWith(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Action<User>> = flow {
        emit(Action.Loading())
        val response =
            runIo { authService.registerWith(name, surname, nickname, email, password).execute() }
        val body = response.bodyOrThrow()

        if (body.status == SUCCESS) emit(Action.Success(data = body.data?.asDomain()))
        else emit(Action.Empty(body.status))

    }.catch { emit(it.toAction()) }

    override suspend fun requestCode(
        token: String
    ): Result<User?> = authService.requestCode(token).map { it.data?.asDomain() }

    override fun checkCode(code: String, token: String): Flow<Action<User>> = flow {
        emit(Action.Loading())
        val response = runIo { authService.verifyEmail(code, token).execute() }
        val body = response.bodyOrThrow()

        when (body.status) {
            SUCCESS -> emit(Action.Success(data = body.data?.asDomain()))
            else -> emit(Action.Empty(body.status))
        }
    }.catch { emit(it.toAction()) }

    override suspend fun cacheUser(user: User) = userDao.cacheUser(user.asDatabaseEntity())

    override fun getUser(): Flow<User?> = userDao.getUser().map { it?.asDomain() }

    override suspend fun checkLoginForAvailability(
        login: String
    ): Action<Boolean> {
        authService
            .checkNicknameForAvailability(login)
            .getOrExceptionAndNull {
                it.toAction<Boolean>()
            }?.let {
                return when (it.status) {
                    SUCCESS -> Action.Success(data = it.data)
                    else -> Action.Empty(status = it.status)
                }
            }
        return Action.Empty()
    }

    override suspend fun checkEmailForAvailability(
        email: String
    ): Action<Boolean> {
        authService
            .checkEmailForAvailability(email)
            .getOrExceptionAndNull {
                it.toAction<Boolean>()
            }?.let {
                return when (it.status) {
                    SUCCESS -> Action.Success(data = it.data)
                    else -> Action.Empty(status = it.status)
                }
            }
        return Action.Empty()
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
        userApi.getFeed(token).getOrExceptionAndNull {
            it.toAction<Boolean>()
        }?.let {
            if (it.status == SUCCESS) emit(Action.Success(data = it.data?.map { it.asDomain() }
                ?: emptyList()))
            else emit(Action.Empty(it.status))
        }
    }

    override fun stopAwaitingFeed() = feedService.close()

    override suspend fun requestPasswordRestoreCode(
        login: String
    ): Action<User?> {
        authService
            .requestPasswordRestoreCode(login)
            .getOrExceptionAndNull {
                it.toAction<Boolean>()
            }?.let {
                return when (it.status) {
                    SUCCESS -> Action.Success(it.data?.asDomain())
                    else -> Action.Empty(status = it.status)
                }
            }
        return Action.Empty()
    }

    override fun restorePasswordBy(
        login: String,
        code: String,
        newPassword: String
    ): Flow<Action<User>> = flow {
        emit(Action.Loading())
        val response = runIo { authService.restorePasswordBy(login, code, newPassword).execute() }
        val body = response.bodyOrThrow()

        when (body.status) {
            SUCCESS -> emit(Action.Success(data = body.data?.asDomain()))
            else -> emit(Action.Empty(body.status))
        }
    }.catch { emit(it.toAction()) }

    override fun createPost(
        token: String,
        label: String,
        content: String,
        imageFile: File?
    ): Flow<Action<Post>> = flow {
        emit(Action.Loading())

        imageFile?.compress { !it.isSvg && !it.isGif }

        val image = imageFile.toMultipartFormData()

        val response = runIo { userApi.createPost(token, label, content, image).execute() }
        val body = response.bodyOrThrow()

        if (body.status == SUCCESS) emit(Action.Success(data = body.data?.asDomain()?.let {
            if (it.images[0].id == "") it.copy(images = emptyList(), comments = emptyList())
            else it.copy(comments = emptyList())
        }))
        else emit(Action.Empty(body.status))

    }.catch { emit(it.toAction()) }
}