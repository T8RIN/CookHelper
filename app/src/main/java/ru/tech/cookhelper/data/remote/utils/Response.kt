package ru.tech.cookhelper.data.remote.utils

data class Response<T>(
    val status: Int,
    val message: String,
    val data: T?
)