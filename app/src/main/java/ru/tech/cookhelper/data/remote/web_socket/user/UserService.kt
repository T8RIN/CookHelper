package ru.tech.cookhelper.data.remote.web_socket.user

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.constants.Constants
import ru.tech.cookhelper.data.remote.web_socket.WebSocketClient
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState

class UserService : WebSocketClient() {

    operator fun invoke(
        chatId: Long, token: String
    ): Flow<WebSocketState> = updateBaseUrl(
        newBaseUrl = "${Constants.WS_BASE_URL}websocket/chat/?token=$token&id=$chatId"
    ).openWebSocket().receiveAsFlow()

}