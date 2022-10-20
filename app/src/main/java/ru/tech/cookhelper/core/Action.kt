package ru.tech.cookhelper.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

sealed class Action<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Action<T>(data)
    class Success<T>(data: T?) : Action<T>(data)
    class Error<T>(message: String?) : Action<T>(message = message)
    class Empty<T>(val status: Int? = null) : Action<T>()
}

inline fun <T> Flow<Action<T>>.onSuccess(
    crossinline action: suspend T.() -> Unit
): Flow<Action<T>> = transform { value ->
    if (value is Action.Success) value.data?.action()
    return@transform emit(value)
}

inline fun <T> Flow<Action<T>>.onLoading(
    crossinline action: suspend T?.() -> Unit
): Flow<Action<T>> = transform { value ->
    if (value is Action.Loading) value.data.action()
    return@transform emit(value)
}

inline fun <T> Flow<Action<T>>.onError(
    crossinline action: suspend String.() -> Unit
): Flow<Action<T>> = transform { value ->
    if (value is Action.Error) action(value.message ?: "")
    return@transform emit(value)
}

inline fun <T> Flow<Action<T>>.onEmpty(
    crossinline action: suspend Int?.() -> Unit
): Flow<Action<T>> = transform { value ->
    if (value is Action.Empty) action(value.status)
    return@transform emit(value)
}

inline fun <T> Action<T>.onSuccess(
    crossinline action: T.() -> Unit
): Action<T> = apply {
    if (this is Action.Success) data?.action()
}

inline fun <T> Action<T>.onLoading(
    crossinline action: T?.() -> Unit
): Action<T> = apply {
    if (this is Action.Loading) data.action()
}

inline fun <T> Action<T>.onError(
    crossinline action: String.() -> Unit
): Action<T> = apply {
    if (this is Action.Error) (message ?: "").action()
}

inline fun <T> Action<T>.onEmpty(
    crossinline action: Int?.() -> Unit
): Action<T> = apply {
    if (this is Action.Empty) status.action()
}