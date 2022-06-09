package ru.tech.cookhelper.domain.use_case.login

import ru.tech.cookhelper.data.remote.api.auth.AuthService
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(
        login: String,
        password: String
    ) = authService.loginWith(login, login, password)
}