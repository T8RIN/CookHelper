package ru.tech.cookhelper.data.remote.web_socket.protocol

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.remote.dto.UserDto
import ru.tech.cookhelper.data.remote.utils.Response
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState

interface UserService : Service {
    operator fun invoke(id: Long, token: String): Flow<WebSocketState<Response<UserDto>>>
}