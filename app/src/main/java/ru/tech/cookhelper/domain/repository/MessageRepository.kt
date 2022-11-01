package ru.tech.cookhelper.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Chat
import ru.tech.cookhelper.domain.model.FormMessage
import ru.tech.cookhelper.domain.model.Message

interface MessageRepository {

    fun getChat(chatId: Long, token: String): Flow<Action<Chat>>

    fun awaitNewMessages(chatId: Long, token: String): Flow<Action<Message>>

    fun sendMessage(message: FormMessage)

    fun stopAwaitingMessages()

    fun getChatList(token: String): Flow<Action<List<Chat>>>

}