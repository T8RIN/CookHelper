package ru.tech.cookhelper.domain.utils.text

sealed class ValidatorResult<S> {
    class Failure<S>(val message: S) : ValidatorResult<S>()
    class NoResult<S> : ValidatorResult<S>()
    class Success<S> : ValidatorResult<S>()
}

inline fun <S> ValidatorResult<S>.onSuccess(
    action: () -> Unit
) = apply {
    if (this is ValidatorResult.Success) action()
}

inline fun <S> ValidatorResult<S>.onFailure(
    action: (S) -> Unit
) = apply {
    if (this is ValidatorResult.Failure) action(this.message)
}

inline fun <S> ValidatorResult<S>.isSuccess(
    action: (Boolean) -> Unit
) = apply {
    action(this is ValidatorResult.Success)
}