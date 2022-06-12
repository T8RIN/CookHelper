package ru.tech.cookhelper.domain.use_case.login

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        login: String,
        password: String
    ) = userRepository.loginWith(login, password)
}