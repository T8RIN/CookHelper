package ru.tech.cookhelper.presentation.app.components

import ru.tech.cookhelper.domain.model.User

data class UserState(
    val user: User? = null,
    val token: String = user?.token ?: ""
)