package ru.tech.cookhelper.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Chat
import ru.tech.cookhelper.domain.model.Message

interface MessageRepository {

    fun getAllMessages(chatId: String, token: String): Flow<Action<List<Message>>>

    fun awaitNewMessages(chatId: String, token: String): Flow<Action<Message>>

    fun sendMessage(message: String)

    fun stopAwaitingMessages()

    fun getChatList(token: String): Flow<Action<List<Chat>>>

}