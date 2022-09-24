package ru.tech.cookhelper.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.data.remote.api.chat.ChatApi
import ru.tech.cookhelper.data.remote.dto.MessageDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.remote.web_socket.message.MessageService
import ru.tech.cookhelper.data.utils.JsonParser
import ru.tech.cookhelper.domain.model.Chat
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.repository.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageService: MessageService,
    private val chatApi: ChatApi,
    private val jsonParser: JsonParser
) : MessageRepository {

    override fun getAllMessages(chatId: String, token: String): Flow<Action<List<Message>>> = flow {
        emit(Action.Loading())
        val response = chatApi.getAllMessages(chatId, token)
        if (response.status == 400) emit(Action.Success(data = response.data?.map { it.asDomain() }
            ?: emptyList()))
        else emit(Action.Error(message = response.message))
    }.catch { emit(Action.Error(message = it.message)) }

    override fun awaitNewMessages(chatId: String, token: String): Flow<Action<Message>> = flow {
        messageService(chatId = chatId, token = token)
            .catch { emit(Action.Error(message = it.message)) }
            .collect { state ->
                when (state) {
                    is WebSocketState.Error -> emit(Action.Error(message = state.message))
                    is WebSocketState.Message -> jsonParser.fromJson<MessageDto>(
                        json = state.text,
                        type = MessageDto::class.java
                    )?.let { emit(Action.Success(data = it.asDomain())) }
                    WebSocketState.Closing -> emit(Action.Loading())
                    is WebSocketState.Opened -> emit(Action.Empty())
                    WebSocketState.Opening -> emit(Action.Loading())
                    WebSocketState.Restarting -> emit(Action.Loading())
                    else -> {}
                }
            }
    }

    override fun sendMessage(message: String) {
        messageService.send(message)
    }

    override fun stopAwaitingMessages() = messageService.close()

    override fun getChatList(token: String): Flow<Action<List<Chat>>> = flow {
        val response = chatApi.getChatList(token)
        Log.d("COCK", response)
    }

}