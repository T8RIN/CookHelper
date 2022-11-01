package ru.tech.cookhelper.domain.use_case.send_message

import ru.tech.cookhelper.domain.model.FormMessage
import ru.tech.cookhelper.domain.repository.MessageRepository
import javax.inject.Inject

class SendMessagesUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke(formMessage: FormMessage) = repository.sendMessage(formMessage)
}