package ru.tech.cookhelper.domain.use_case.check_login

import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class CheckLoginForAvailabilityUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        login: String
    ): Action<Boolean> = userRepository.checkLoginForAvailability(login)
}
