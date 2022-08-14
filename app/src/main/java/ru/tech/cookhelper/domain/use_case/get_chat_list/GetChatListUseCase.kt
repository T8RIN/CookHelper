package ru.tech.cookhelper.domain.use_case.get_chat_list

import ru.tech.cookhelper.domain.repository.MessageRepository
import javax.inject.Inject

class GetChatListUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke(token: String) = repository.getChatList(token)
}