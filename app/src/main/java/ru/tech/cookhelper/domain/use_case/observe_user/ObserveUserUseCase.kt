package ru.tech.cookhelper.domain.use_case.observe_user

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class ObserveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        id: Long,
        token: String
    ) = userRepository.observeUser(
        id = id,
        token = token
    )
}