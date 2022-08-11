package ru.tech.cookhelper.data.remote.webSocket.message

sealed class WebSocketEvent {
    class Error(val message: String) : WebSocketEvent()
    class Message(val text: String) : WebSocketEvent()
}