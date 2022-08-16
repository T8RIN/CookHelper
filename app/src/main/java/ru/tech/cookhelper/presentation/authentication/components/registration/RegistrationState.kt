package ru.tech.cookhelper.presentation.authentication.components.registration

import ru.tech.cookhelper.domain.model.User

data class RegistrationState(
    val isLoading: Boolean = false,
    val user: User? = null
)
