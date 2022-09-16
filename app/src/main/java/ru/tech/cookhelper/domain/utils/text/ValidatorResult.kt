package ru.tech.cookhelper.domain.utils.text

sealed class ValidatorResult<S> {
    class Error<S>(val message: S) : ValidatorResult<S>()
    class NoResult<S> : ValidatorResult<S>()
    class Success<S> : ValidatorResult<S>()
}