package ru.tech.cookhelper.domain.use_case.get_all_messages

import ru.tech.cookhelper.domain.repository.MessageRepository
import javax.inject.Inject

class GetAllMessagesUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke(chatId: String, token: String) = repository.getAllMessages(chatId, token)
}