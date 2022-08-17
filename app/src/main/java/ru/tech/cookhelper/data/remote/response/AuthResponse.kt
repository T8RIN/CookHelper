package ru.tech.cookhelper.data.remote.response

import ru.tech.cookhelper.data.remote.dto.UserDto
import ru.tech.cookhelper.data.remote.utils.Response

data class AuthResponse(
    override val message: String,
    override val status: Int,
    val user: UserDto?
) : Response
