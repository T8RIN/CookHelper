package ru.tech.cookhelper.data.remote.web_socket.protocol

interface Service {
    fun sendMessage(data: String)
    fun closeService()
}