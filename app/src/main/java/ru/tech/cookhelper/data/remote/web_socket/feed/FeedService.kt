package ru.tech.cookhelper.data.remote.web_socket.feed

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.constants.Constants
import ru.tech.cookhelper.data.remote.web_socket.WebSocketClient
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState

class FeedService : WebSocketClient() {

    operator fun invoke(
        token: String
    ): Flow<WebSocketState> = updateBaseUrl(
        newBaseUrl = "${Constants.WS_BASE_URL}ws/feed/?token=$token"
    ).openWebSocket().receiveAsFlow()

}