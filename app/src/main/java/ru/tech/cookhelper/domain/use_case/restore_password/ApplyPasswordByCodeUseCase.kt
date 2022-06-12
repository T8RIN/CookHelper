package ru.tech.cookhelper.domain.use_case.restore_password

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class ApplyPasswordByCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        code: String,
        newPassword: String
    ) = userRepository.restorePasswordBy(code, newPassword)
}