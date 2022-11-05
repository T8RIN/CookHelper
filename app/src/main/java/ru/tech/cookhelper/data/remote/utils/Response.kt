package ru.tech.cookhelper.data.remote.utils

data class Response<T>(
    val status: Int,
    val data: T?
)