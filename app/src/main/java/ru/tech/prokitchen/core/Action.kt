package ru.tech.prokitchen.core

sealed class Action<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Action<T>(data)
    class Empty<T>(message: String?) : Action<T>(message = message)
    class Loading<T>(data: T? = null) : Action<T>(data)
}