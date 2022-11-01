package ru.tech.cookhelper.data.remote.web_socket

import okhttp3.Response

sealed class WebSocketState {
    class Message(val text: String) : WebSocketState()
    class Error(val t: Throwable) : WebSocketState()
    object Opening : WebSocketState()
    class Opened(val response: Response) : WebSocketState()
    object Restarting : WebSocketState()
    object Closing : WebSocketState()
    object Closed : WebSocketState()
}