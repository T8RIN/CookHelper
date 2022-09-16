package ru.tech.cookhelper.domain.utils.text.validators

import ru.tech.cookhelper.domain.utils.text.TextValidator
import ru.tech.cookhelper.domain.utils.text.ValidatorResult

class HasNumberTextValidator<S>(
    private val message: S,
    private val countOfNumbers: Int = 1
) : TextValidator<S> {
    override var validatorResult: ValidatorResult<S> = ValidatorResult.NoResult()
    override fun validate(stringToValidate: String): ValidatorResult<S> {
        val count = stringToValidate.count { it.isDigit() }
        validatorResult = if (count >= countOfNumbers) {
            ValidatorResult.Success()
        } else ValidatorResult.Error(message)

        return validatorResult
    }
}