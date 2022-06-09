package ru.tech.cookhelper.data.remote.api.auth

data class AuthInfo(
    val message: String,
    val status: Int,
    val user: User
)
