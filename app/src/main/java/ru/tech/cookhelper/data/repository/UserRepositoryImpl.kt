package ru.tech.cookhelper.data.repository

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
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.remote.web_socket.feed.FeedService
import ru.tech.cookhelper.data.remote.web_socket.user.UserService
import ru.tech.cookhelper.domain.model.Post
import ru.tech.cookhelper.domain.model.RecipePost
import ru.tech.cookhelper.domain.model.Topic
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
    private val userService: UserService
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
            .catch { emit(it.toAction()) }
            .collect { state ->
                when (state) {
                    is WebSocketState.Error -> emit(state.t.toAction())
                    is WebSocketState.Message -> emit(Action.Success(data = state.obj?.map { it.asDomain() }))
                    is WebSocketState.Opening,
                    is WebSocketState.Restarting,
                    is WebSocketState.Closing -> emit(Action.Loading())
                    is WebSocketState.Closed, is WebSocketState.Opened -> emit(Action.Empty())
                }
            }
//        emit(Action.Loading())
//        userApi.getFeed(token).getOrExceptionAndNull {
//            it.toAction<Boolean>()
//        }?.let {
//            if (it.status == SUCCESS) emit(Action.Success(data = it.data?.map { it.asDomain() }
//                ?: emptyList()))
//            else emit(Action.Empty(it.status))
//        }
    }

    override fun stopAwaitingFeed() = feedService.closeService()

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
        imageFile: File?,
        type: String
    ): Flow<Action<Post>> = flow {
        emit(Action.Loading())

        imageFile?.compress { !it.isSvg && !it.isGif }

        val image = imageFile.toMultipartFormData(type)

        val response = runIo { userApi.createPost(token, label, content, image).execute() }
        val body = response.bodyOrThrow()

        if (body.status == SUCCESS) emit(Action.Success(data = body.data?.asDomain()))
        else emit(Action.Empty(body.status))

    }.catch { emit(it.toAction()) }

    override fun createTopic(
        token: String,
        title: String,
        text: String,
        attachments: List<Pair<File?, String>>,
        tags: List<String>
    ): Flow<Action<Topic>> = flow {
        emit(Action.Loading())

        attachments.firstOrNull()?.first?.compress { !it.isSvg && !it.isGif }

        val _attachments = attachments.firstOrNull()?.first.toMultipartFormData(
            attachments.firstOrNull()?.second ?: ""
        )

        val response =
            runIo { userApi.createTopic(token, title, text, _attachments, tags).execute() }
        val body = response.bodyOrThrow()

        if (body.status == SUCCESS) emit(Action.Success(data = body.data?.asDomain()))
        else emit(Action.Empty(body.status))
    }.catch { emit(it.toAction()) }

    override fun observeUser(
        id: Long,
        token: String
    ): Flow<Action<User>> = flow {
        userService(token = token, id = id)
            .catch { emit(it.toAction()) }
            .collect { state ->
                when (state) {
                    is WebSocketState.Error -> emit(state.t.toAction())
                    is WebSocketState.Message -> emit(Action.Success(data = state.obj?.data?.asDomain()))
                    is WebSocketState.Opening,
                    is WebSocketState.Restarting,
                    is WebSocketState.Closing -> emit(Action.Loading())
                    is WebSocketState.Closed, is WebSocketState.Opened -> emit(Action.Empty())
                }
            }
    }
}