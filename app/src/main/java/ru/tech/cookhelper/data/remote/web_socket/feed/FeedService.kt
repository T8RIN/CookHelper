package ru.tech.cookhelper.data.remote.web_socket.feed

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.tech.cookhelper.core.constants.Constants
import ru.tech.cookhelper.data.remote.web_socket.WebSocketClient
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState

class FeedService : WebSocketClient() {

    override var baseUrl: String = ""

    operator fun invoke(token: String): Flow<WebSocketState> {
        baseUrl = "${Constants.WS_BASE_URL}ws/feed/?token=$token"
        openWebSocket()
        return webSocketState.receiveAsFlow()
    }

}