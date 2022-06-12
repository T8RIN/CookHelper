package ru.tech.cookhelper.presentation.authentication.components

import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.ui.utils.UIText

data class RestorePasswordState(
    val isLoading: Boolean = false,
    val found: Boolean = false,
    val error: UIText = UIText.DynamicString(""),
    val state: RestoreState = RestoreState.Login,
    val user: User? = null
)
