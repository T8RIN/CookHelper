package ru.tech.cookhelper.data.remote.web_socket.message

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.remote.dto.MessageDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.remote.web_socket.Service

interface MessageService : Service {
    operator fun invoke(chatId: Long, token: String): Flow<WebSocketState<MessageDto>>
}