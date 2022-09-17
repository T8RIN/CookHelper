package ru.tech.cookhelper.domain.utils.text.validators

import ru.tech.cookhelper.domain.utils.text.TextValidator
import ru.tech.cookhelper.domain.utils.text.ValidatorResult

class NonEmptyTextValidator<S>(
    private val message: S
) : TextValidator<S> {
    override var validatorResult: ValidatorResult<S> = ValidatorResult.NoResult()
    override fun validate(stringToValidate: String): ValidatorResult<S> {
        validatorResult = if (stringToValidate.trim().isEmpty())
            ValidatorResult.Failure(message)
        else ValidatorResult.Success()

        return validatorResult
    }
}