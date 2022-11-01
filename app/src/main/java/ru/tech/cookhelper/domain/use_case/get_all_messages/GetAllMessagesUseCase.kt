package ru.tech.cookhelper.domain.use_case.get_all_messages

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Chat
import ru.tech.cookhelper.domain.repository.MessageRepository
import javax.inject.Inject

class GetChatUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke(
        chatId: Long,
        token: String
    ): Flow<Action<Chat>> = repository.getChat(chatId, token)
}