package ru.tech.cookhelper.domain.utils.text

interface TextValidator<S> {
    fun validate(stringToValidate: String): ValidatorResult<S>
    var validatorResult: ValidatorResult<S>
}