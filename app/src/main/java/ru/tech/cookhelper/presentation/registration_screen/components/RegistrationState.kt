package ru.tech.cookhelper.presentation.registration_screen.components

import ru.tech.cookhelper.domain.model.User

data class RegistrationState(
    val isLoading: Boolean = false,
    val user: User? = null
)
