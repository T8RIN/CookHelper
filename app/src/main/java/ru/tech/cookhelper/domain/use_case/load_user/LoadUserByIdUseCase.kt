package ru.tech.cookhelper.domain.use_case.load_user

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class LoadUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: String) = userRepository.loadUserById(id)
}