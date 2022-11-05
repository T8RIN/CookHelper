package ru.tech.cookhelper.data.remote.web_socket.user

import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.constants.Constants
import ru.tech.cookhelper.data.remote.dto.UserDto
import ru.tech.cookhelper.data.remote.utils.Response
import ru.tech.cookhelper.data.remote.web_socket.WebSocketClient
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.utils.JsonParser
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    jsonParser: JsonParser
) : WebSocketClient<Response<UserDto>>(jsonParser = jsonParser), UserService {

    override operator fun invoke(
        id: Long,
        token: String
    ): Flow<WebSocketState<Response<UserDto>>> = updateBaseUrl(
        newBaseUrl = "${Constants.WS_BASE_URL}websocket/user/?id=$id&token=$token"
    ).setType(
        Types.newParameterizedType(Response::class.java, UserDto::class.java)
    ).openWebSocket().receiveAsFlow()

    override fun sendMessage(data: String) = send(data)

    override fun closeService() = close()

}