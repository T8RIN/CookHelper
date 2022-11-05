package ru.tech.cookhelper.data.remote.web_socket

import okhttp3.Response

sealed class WebSocketState<T> {
    class Message<T>(val obj: T?) : WebSocketState<T>()
    class Error<T>(val t: Throwable) : WebSocketState<T>()
    class Opening<T> : WebSocketState<T>()
    class Opened<T>(val response: Response) : WebSocketState<T>()
    class Restarting<T> : WebSocketState<T>()
    class Closing<T> : WebSocketState<T>()
    class Closed<T> : WebSocketState<T>()
}