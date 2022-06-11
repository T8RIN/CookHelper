package ru.tech.cookhelper.domain.use_case.check_code

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class CheckCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        code: String,
        token: String
    ) = userRepository.checkCode(code, token)
}