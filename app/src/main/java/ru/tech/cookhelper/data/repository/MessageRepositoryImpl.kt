package ru.tech.cookhelper.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.data.remote.webSocket.message.MessageService
import ru.tech.cookhelper.data.remote.webSocket.message.WebSocketEvent
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.repository.MessageRepository

class MessageRepositoryImpl(
    private val messageService: MessageService = MessageService()
) : MessageRepository {

    override fun getAllMessages(chatId: String, message: String): Flow<Action<List<Message>>> {
        TODO("Not yet implemented")
    }

    override fun awaitNewMessages(chatId: String, token: String): Flow<Action<Message>> = flow {
        messageService
            .awaitNewMessages(chatId, token)
            .collect { event ->
                when (event) {
                    is WebSocketEvent.Error -> emit(Action.Error(message = event.message))
                    is WebSocketEvent.Message -> emit(Action.Success(data = Message(event.text)))
                }
            }
    }

    override fun sendMessage(message: String) {
        messageService.send(message)
    }

}