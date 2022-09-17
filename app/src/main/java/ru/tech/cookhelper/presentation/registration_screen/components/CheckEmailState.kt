package ru.tech.cookhelper.presentation.registration_screen.components

import ru.tech.cookhelper.presentation.ui.utils.compose.UIText

data class CheckEmailState(
    val isValid: Boolean = false,
    val error: UIText = UIText.Empty(),
    val isLoading: Boolean = false
)