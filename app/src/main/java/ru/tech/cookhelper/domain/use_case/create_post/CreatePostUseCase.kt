package ru.tech.cookhelper.domain.use_case.create_post

import ru.tech.cookhelper.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        label: String,
        content: String,
        imageFile: File?,
        type: String
    ) = userRepository.createPost(
        token = token,
        label = label,
        content = content,
        imageFile = imageFile,
        type = type
    )
}