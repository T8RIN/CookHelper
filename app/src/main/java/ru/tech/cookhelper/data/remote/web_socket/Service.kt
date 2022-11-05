package ru.tech.cookhelper.data.remote.web_socket

interface Service {
    fun sendMessage(data: String)
    fun closeService()
}