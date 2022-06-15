package ru.tech.cookhelper.presentation.authentication.components

import ru.tech.cookhelper.presentation.ui.utils.UIText

data class CheckLoginState(
    val error: UIText = UIText.DynamicString("")
)