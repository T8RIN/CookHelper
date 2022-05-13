package ru.tech.cookhelper.core

sealed class Action<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Action<T>(data)
    class Success<T>(data: T) : Action<T>(data)
    class Error<T>(message: String?) : Action<T>(message = message)
    class Empty<T> : Action<T>()
}