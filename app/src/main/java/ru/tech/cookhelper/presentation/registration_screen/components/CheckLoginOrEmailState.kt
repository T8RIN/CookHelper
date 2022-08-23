package ru.tech.cookhelper.presentation.registration_screen.components

import ru.tech.cookhelper.presentation.ui.utils.compose.UIText

data class CheckLoginOrEmailState(
    val error: UIText = UIText.DynamicString(""),
    val isLoading: Boolean = false
)