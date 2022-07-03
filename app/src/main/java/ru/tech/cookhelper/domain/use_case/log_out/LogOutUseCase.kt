package ru.tech.cookhelper.domain.use_case.log_out

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.logOut()
}
