package ru.tech.cookhelper.domain.use_case.stop_awaiting_messages

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.repository.MessageRepository
import javax.inject.Inject

class StopAwaitingMessagesUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke() = repository.stopAwaitingMessages()
}