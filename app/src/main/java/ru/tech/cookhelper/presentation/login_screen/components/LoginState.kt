package ru.tech.cookhelper.presentation.login_screen.components

import ru.tech.cookhelper.domain.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null
)
