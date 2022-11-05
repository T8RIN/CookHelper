package ru.tech.cookhelper.data.remote.web_socket.user

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.remote.dto.UserDto
import ru.tech.cookhelper.data.remote.utils.Response
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.remote.web_socket.Service

interface UserService : Service {
    operator fun invoke(id: Long, token: String): Flow<WebSocketState<Response<UserDto>>>
}