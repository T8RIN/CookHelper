package ru.tech.cookhelper.data.remote.webSocket


import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import okhttp3.*
import ru.tech.cookhelper.data.remote.webSocket.message.WebSocketEvent

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
    val webSocketEvents: Channel<WebSocketEvent> = Channel(10)

    /**
     * Открываем веб-сокет
     */
    @Synchronized
    fun openWebSocket() {
        if (opening)
            return
        Log.d(SOCKET_TAG, "opening")

        opening = true
        socketOpen = false

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

        restarting = true

        if (socketOpen) closeWebSocket()

        CoroutineScope(Dispatchers.IO).launch {
            delay(5000L)
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
        try {
            webSocket?.close(CLOSE_CODE, CLOSE_REASON)
            socketOpen = false
        } catch (e: Exception) {
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
        webSocketEvents.trySend(WebSocketEvent.Error(message = t.message.toString()))
        // Переоткрываем
        restartWebSocket()
    }

    /**
     * Сокет закрылся
     */
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d(SOCKET_TAG, "onClosed")
    }

    /**
     * Ловим сообщения
     */
    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        webSocketEvents.trySend(WebSocketEvent.Message(text))
        Log.d(SOCKET_TAG, "received: $text")
    }

    companion object {
        private const val CLOSE_REASON: String = "Normal closure"
        private const val CLOSE_CODE: Int = 100
        private val SOCKET_TAG: String = "WEB_SOCKET_CLIENT - ${this::class}"
    }
}