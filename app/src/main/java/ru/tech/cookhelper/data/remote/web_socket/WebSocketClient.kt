package ru.tech.cookhelper.data.remote.web_socket


import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.*
import ru.tech.cookhelper.data.utils.JsonParser
import java.io.Closeable
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

@Suppress("PrivatePropertyName", "MemberVisibilityCanBePrivate")
abstract class WebSocketClient<T>(
    private var baseUrl: String = "",
    private val jsonParser: JsonParser,
    private var type: Type = Nothing::class.java
) : WebSocketListener(), Closeable {

    protected open val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(40, TimeUnit.SECONDS)
        .connectTimeout(40, TimeUnit.SECONDS)
        .pingInterval(40, TimeUnit.SECONDS)
        .hostnameVerifier { _, _ -> true }
        .build()

    /**
     * Открывается ли в данный момент сокет
     */
    protected open var opening: Boolean = false

    /**
     * Перезапускается ли в данный момент сокет
     */
    protected open var restarting: Boolean = false

    /**
     * Открыт ли в данный момент сокет
     */
    open var socketOpen: Boolean = false

    /**
     * Ссылка на сокет
     */
    protected var webSocket: WebSocket? = null

    /**
     * Канал с обновлениями сокета
     */
    private val _webSocketState: Channel<WebSocketState<T>> = Channel(50)
    val webSocketState: ReceiveChannel<WebSocketState<T>> = _webSocketState

    /**
     * Открываем веб-сокет
     */
    @Synchronized
    fun openWebSocket() = apply {
        require(baseUrl.isNotEmpty()) { "Base url cannot be empty" }
        require(type != Nothing::class.java) { "JsonParser cannot parse object, type is not present" }

        if (opening) return this

        if (socketOpen) closeWebSocket()

        Log.d(SOCKET_TAG, "opening")
        opening = true
        _webSocketState.trySend(WebSocketState.Opening())

        val request = Request.Builder()
            .url(baseUrl)
            .build()
        okHttpClient.newWebSocket(request, this)
    }

    /**
     * Перезапускаем веб-сокет
     */
    @Synchronized
    fun restartWebSocket() {
        if (restarting) return

        Log.d(SOCKET_TAG, "restarting")
        _webSocketState.trySend(WebSocketState.Restarting())

        restarting = true

        if (socketOpen) closeWebSocket()

        CoroutineScope(Dispatchers.IO).launch {
            delay(4000L)
            restarting = false
            opening = false
            openWebSocket()
            cancel()
        }
    }

    /**
     * Отправка данных в сокет
     */
    fun send(data: String) {
        val sent = webSocket?.send(data)
        Log.d(SOCKET_TAG, "sent is $sent: $data")
    }

    /**
     * Закрываем веб-сокет сами
     */
    @Synchronized
    private fun closeWebSocket() {
        Log.d(SOCKET_TAG, "closing")
        _webSocketState.trySend(WebSocketState.Closing())
        try {
            webSocket?.close(CLOSE_CODE, CLOSE_REASON)
            socketOpen = false
        } catch (e: Exception) {
            _webSocketState.trySend(WebSocketState.Error(e))
            Log.d(SOCKET_TAG, "closeWebSocket error")
            e.printStackTrace()
        }
    }

    @Synchronized
    override fun close() = closeWebSocket()

    /**
     * Сокет открылся
     */
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d(SOCKET_TAG, "onOpen")
        _webSocketState.trySend(WebSocketState.Opened(response))

        this.webSocket = webSocket
        socketOpen = true
        opening = false
    }

    /**
     * Произошла магическая ошибка - сокет перестает отвечать
     */
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)

        Log.d(SOCKET_TAG, "onFailure: $t")
        t.printStackTrace()

        _webSocketState.trySend(WebSocketState.Error(t))
        // Переоткрываем
        restartWebSocket()
    }

    /**
     * Сокет закрылся
     */
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        _webSocketState.trySend(WebSocketState.Closed())
        Log.d(SOCKET_TAG, "onClosed")
    }

    /**
     * Ловим сообщения
     */
    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        _webSocketState.trySend(WebSocketState.Message(jsonParser.fromJson(text, type)))
        Log.d(SOCKET_TAG, "received: $text")
    }

    fun setBaseUrl(newBaseUrl: String) = apply {
        baseUrl = newBaseUrl
    }

    fun setType(newType: Type) = apply {
        type = newType
    }

    fun receiveAsFlow(): Flow<WebSocketState<T>> = webSocketState.receiveAsFlow()

    private val CLOSE_REASON: String = "Normal closure"
    private val CLOSE_CODE: Int = 1000
    private val SOCKET_TAG: String = this::class.java.simpleName
}