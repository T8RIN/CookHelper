package ru.tech.cookhelper.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

sealed class Action<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Action<T>(data)
    class Success<T>(data: T?) : Action<T>(data)
    class Error<T>(message: String?) : Action<T>(message = message)
    class Empty<T>(val status: Int? = null) : Action<T>()
}

typealias BindableFlow<S, T> = Flow<BindableAction<S, T>>
typealias BindableAction<S, T> = Pair<S, Action<T>>

val <S, T> BindableAction<S, T>.state: S
    get() = this.first

val <S, T> BindableAction<S, T>.action: Action<T>
    get() = this.second

fun <S, T> Flow<Action<T>>.bindTo(data: S): BindableFlow<S, T> = map { data to it }

inline fun <S, T> BindableFlow<S, T>.onSuccess(
    crossinline action: suspend T?.(S) -> Unit
): BindableFlow<S, T> = transform { value ->
    if (value.action is Action.Success) value.action.data?.action(value.state)
    return@transform emit(value)
}

inline fun <S, T> BindableFlow<S, T>.onLoading(
    crossinline action: suspend T?.(S) -> Unit
): BindableFlow<S, T> = transform { value ->
    if (value.action is Action.Loading) value.action.data?.action(value.state)
    return@transform emit(value)
}

inline fun <S, T> BindableFlow<S, T>.onError(
    crossinline action: suspend String.(S) -> Unit
): BindableFlow<S, T> = transform { value ->
    if (value.action is Action.Error) (value.action.message ?: "").action(value.state)
    return@transform emit(value)
}

inline fun <S, T> BindableFlow<S, T>.onEmpty(
    crossinline action: suspend Int?.(S) -> Unit
): BindableFlow<S, T> = transform { value ->
    if (value.action is Action.Empty) (value.action as Action.Empty<T>).status.action(value.state)
    return@transform emit(value)
}