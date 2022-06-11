package ru.tech.cookhelper.presentation.authentication.components

import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.ui.utils.UIText

data class RegistrationState(
    val isLoading: Boolean = false,
    val error: UIText = UIText.DynamicString(""),
    val user: User? = null
)
