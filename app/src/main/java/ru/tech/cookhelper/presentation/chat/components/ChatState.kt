package ru.tech.cookhelper.presentation.chat.components

import ru.tech.cookhelper.presentation.ui.utils.UIText

data class ChatState(
    val isLoading: Boolean = false,
    val errorMessage: UIText = UIText.empty()
)
