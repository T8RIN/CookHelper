package ru.tech.cookhelper.domain.use_case.get_user

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUser()
}