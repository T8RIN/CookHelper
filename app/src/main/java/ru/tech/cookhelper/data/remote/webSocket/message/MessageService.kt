package ru.tech.cookhelper.data.remote.webSocket.message

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.OkHttpClient
import ru.tech.cookhelper.core.constants.Constants
import ru.tech.cookhelper.core.utils.RetrofitUtils.setTimeout
import ru.tech.cookhelper.data.remote.webSocket.WebSocketClient
import java.util.concurrent.TimeUnit

class MessageService : WebSocketClient() {

    fun awaitNewMessages(chatId: String, token: String): Flow<WebSocketEvent> {
        baseUrl = "${Constants.WS_BASE_URL}ws/chat/$chatId/?token=qwe"
        openWebSocket()
        return webSocketEvents.receiveAsFlow()
    }

    override val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .setTimeout()
            .pingInterval(40, TimeUnit.SECONDS)
            .hostnameVerifier { _, _ -> true }
            .build()

    override var baseUrl: String = ""

}