package ru.tech.cookhelper.data.remote.web_socket.message

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.constants.Constants
import ru.tech.cookhelper.data.remote.dto.MessageDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketClient
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.remote.web_socket.protocol.MessageService
import ru.tech.cookhelper.data.utils.JsonParser
import javax.inject.Inject

class MessageServiceImpl @Inject constructor(
    jsonParser: JsonParser,
) : WebSocketClient<MessageDto>(jsonParser = jsonParser), MessageService {

    override operator fun invoke(
        chatId: Long, token: String
    ): Flow<WebSocketState<MessageDto>> = updateBaseUrl(
        newBaseUrl = "${Constants.WS_BASE_URL}websocket/chat/?token=$token&id=$chatId"
    ).setType(MessageDto::class.java).openWebSocket().receiveAsFlow()

    override fun sendMessage(data: String) = send(data)

    override fun closeService() = close()

}