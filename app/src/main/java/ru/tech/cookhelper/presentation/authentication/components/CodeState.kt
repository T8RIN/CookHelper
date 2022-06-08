package ru.tech.cookhelper.presentation.authentication.components

data class CodeState(
    val error: String = "",
    val matched: Boolean = false,
    val isLoading: Boolean = false,
)
