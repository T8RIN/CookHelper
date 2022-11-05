package ru.tech.cookhelper.data.remote.web_socket.protocol

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.remote.dto.MessageDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState

interface MessageService : Service {
    operator fun invoke(chatId: Long, token: String): Flow<WebSocketState<MessageDto>>
}