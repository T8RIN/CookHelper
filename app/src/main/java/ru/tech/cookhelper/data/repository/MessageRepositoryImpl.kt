package ru.tech.cookhelper.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.core.constants.Status.SUCCESS
import ru.tech.cookhelper.core.utils.kotlin.runIo
import ru.tech.cookhelper.data.remote.api.chat.ChatApi
import ru.tech.cookhelper.data.remote.dto.MessageDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.remote.web_socket.message.MessageService
import ru.tech.cookhelper.data.utils.JsonParser
import ru.tech.cookhelper.domain.model.Chat
import ru.tech.cookhelper.domain.model.FormMessage
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.repository.MessageRepository
import ru.tech.cookhelper.presentation.ui.utils.toAction
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageService: MessageService,
    private val chatApi: ChatApi,
    private val jsonParser: JsonParser
) : MessageRepository {

    override fun getChat(chatId: Long, token: String): Flow<Action<Chat>> = flow {
        emit(Action.Loading())
        val response = runIo { chatApi.getChat(chatId, token) }
        if (response.status == SUCCESS) emit(Action.Success(data = response.data?.asDomain()))
        else emit(Action.Empty(status = response.status))
    }.catch { emit(it.toAction()) }

    override fun awaitNewMessages(chatId: Long, token: String): Flow<Action<Message>> = flow {
        messageService(chatId = chatId, token = token)
            .catch { it.toAction<Message>() }
            .collect { state ->
                when (state) {
                    is WebSocketState.Error -> emit(state.t.toAction())
                    is WebSocketState.Message -> jsonParser.fromJson<MessageDto>(
                        json = state.text,
                        type = MessageDto::class.java
                    )?.let { emit(Action.Success(data = it.asDomain())) }
                    WebSocketState.Closing -> emit(Action.Loading())
                    is WebSocketState.Opened -> emit(Action.Empty())
                    WebSocketState.Opening -> emit(Action.Loading())
                    WebSocketState.Restarting -> emit(Action.Loading())
                    WebSocketState.Closed -> Unit
                }
            }
    }

    override fun sendMessage(message: FormMessage) {
        messageService.send(jsonParser.toJson(message, FormMessage::class.java) ?: "")
    }

    override fun stopAwaitingMessages() = messageService.close()

    override fun getChatList(token: String): Flow<Action<List<Chat>>> = flow {
        val response = chatApi.getChatList(token)
    }

}