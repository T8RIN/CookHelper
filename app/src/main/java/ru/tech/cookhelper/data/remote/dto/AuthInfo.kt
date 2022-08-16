package ru.tech.cookhelper.data.remote.dto

data class AuthInfo(
    val message: String,
    val status: Int,
    val user: UserDto?
)
