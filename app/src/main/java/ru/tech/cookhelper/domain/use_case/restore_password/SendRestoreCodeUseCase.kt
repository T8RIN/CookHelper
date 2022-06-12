package ru.tech.cookhelper.domain.use_case.restore_password

import ru.tech.cookhelper.data.remote.api.auth.AuthInfo
import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class SendRestoreCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        login: String
    ): Result<AuthInfo> = userRepository.requestPasswordRestoreCode(login)
}