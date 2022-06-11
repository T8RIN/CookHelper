package ru.tech.cookhelper.domain.use_case.registration

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ) = userRepository.registerWith(name, surname, nickname, email, password)
}