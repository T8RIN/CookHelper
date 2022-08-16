package ru.tech.cookhelper.data.remote.webSocket


import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import okhttp3.*
import ru.tech.cookhelper.core.utils.ReflectionUtils.name

abstract class WebSocketClient : WebSocketListener() {
    protected abstract val okHttpClient: OkHttpClient
    protected abstract val baseUrl: String

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
    private val _webSocketState: Channel<WebSocketState> = Channel(50)
    val webSocketState: ReceiveChannel<WebSocketState> = _webSocketState

    /**
     * Открываем веб-сокет
     */
    @Synchronized
    fun openWebSocket() {
        if (opening) return
        Log.d(SOCKET_TAG, "opening")

        opening = true
        socketOpen = false

        _webSocketState.trySend(WebSocketState.Opening)

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
        _webSocketState.trySend(WebSocketState.Restarting)

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
        webSocket?.send(data)
    }

    /**
     * Закрываем веб-сокет сами
     */
    @Synchronized
    private fun closeWebSocket() {
        Log.d(SOCKET_TAG, "closing")
        _webSocketState.trySend(WebSocketState.Closing)
        try {
            webSocket?.close(CLOSE_CODE, CLOSE_REASON)
            socketOpen = false
        } catch (e: Exception) {
            _webSocketState.trySend(WebSocketState.Error("closeWebSocket error"))
            Log.d(SOCKET_TAG, "closeWebSocket error")
            e.printStackTrace()
        }
    }

    @Synchronized
    fun close() {
        closeWebSocket()
    }

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
        Log.d(SOCKET_TAG, "onFailure: ${t.message}")
        _webSocketState.trySend(WebSocketState.Error(t.message.toString()))
        // Переоткрываем
        CoroutineScope(Dispatchers.IO).launch {
            delay(500L)
            restartWebSocket()
            cancel()
        }
    }

    /**
     * Сокет закрылся
     */
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        _webSocketState.trySend(WebSocketState.Closed)
        Log.d(SOCKET_TAG, "onClosed")
    }

    /**
     * Ловим сообщения
     */
    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        _webSocketState.trySend(WebSocketState.Message(text))
        Log.d(SOCKET_TAG, "received: $text")
    }

    private val CLOSE_REASON: String = "Normal closure"
    private val CLOSE_CODE: Int = 1000
    private val SOCKET_TAG: String = "WEB_SOCKET_CLIENT - ${this::class.name}"
}