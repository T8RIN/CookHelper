package ru.tech.cookhelper.domain.use_case.check_login

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class CheckLoginOrEmailForAvailabilityUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(query: String) =
        userRepository.checkLoginOrEmailForAvailability(query)
}
