package ru.tech.cookhelper.domain.use_case.await_new_messages

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.repository.MessageRepository
import javax.inject.Inject

class AwaitNewMessagesUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke(
        chatId: Long,
        token: String
    ): Flow<Action<Message>> = repository.awaitNewMessages(chatId, token)
}