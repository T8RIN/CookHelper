package ru.tech.cookhelper.domain.use_case.check_email

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class CheckEmailForAvailabilityUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String) =
        userRepository.checkEmailForAvailability(email)
}
