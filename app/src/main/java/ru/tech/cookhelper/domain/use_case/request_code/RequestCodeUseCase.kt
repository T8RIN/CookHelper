package ru.tech.cookhelper.domain.use_case.request_code

import ru.tech.cookhelper.data.remote.api.auth.AuthInfo
import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class RequestCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        token: String
    ): Result<AuthInfo> = userRepository.requestCode(token)
}
