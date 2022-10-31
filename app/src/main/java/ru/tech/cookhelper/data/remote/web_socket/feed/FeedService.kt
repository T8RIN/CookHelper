package ru.tech.cookhelper.data.remote.web_socket.feed

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tech.cookhelper.data.remote.web_socket.WebSocketClient
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState

class FeedService : WebSocketClient() {

    operator fun invoke(
        token: String
    ): Flow<WebSocketState> = flow {
        emit(WebSocketState.Opening)
    }
    //updateBaseUrl(
    //        newBaseUrl = "${Constants.WS_BASE_URL}ws/feed/?token=$token"
    //    ).openWebSocket().receiveAsFlow()

}