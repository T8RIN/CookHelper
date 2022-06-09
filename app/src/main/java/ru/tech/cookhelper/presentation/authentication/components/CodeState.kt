package ru.tech.cookhelper.presentation.authentication.components

import ru.tech.cookhelper.presentation.ui.utils.UIText

data class CodeState(
    val error: UIText = UIText.DynamicString(""),
    val matched: Boolean = false,
    val isLoading: Boolean = false,
)
