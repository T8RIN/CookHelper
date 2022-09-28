package ru.tech.cookhelper.data.remote.web_socket.message

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.constants.Constants
import ru.tech.cookhelper.data.remote.web_socket.WebSocketClient
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState

class MessageService : WebSocketClient() {

    operator fun invoke(
        chatId: String, token: String
    ): Flow<WebSocketState> = updateBaseUrl(
        newBaseUrl = "${Constants.WS_BASE_URL}ws/chat/$chatId/?token=$token"
    ).openWebSocket().receiveAsFlow()

}