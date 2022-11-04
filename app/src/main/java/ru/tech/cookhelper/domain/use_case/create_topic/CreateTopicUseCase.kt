package ru.tech.cookhelper.domain.use_case.create_topic

import ru.tech.cookhelper.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class CreateTopicUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        title: String,
        text: String,
        attachments: List<Pair<File?, String>>,
        tags: List<String>
    ) = userRepository.createTopic(token, title, text, attachments, tags)
}