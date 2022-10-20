package ru.tech.cookhelper.domain.utils.text.validators

import ru.tech.cookhelper.domain.utils.text.TextValidator
import ru.tech.cookhelper.domain.utils.text.ValidatorResult

class LengthTextValidator<S>(
    private val minLength: Int? = null,
    private val maxLength: Int? = null,
    private val message: (minNotReached: Boolean, maxOverflowed: Boolean) -> S
) : TextValidator<S> {
    override var validatorResult: ValidatorResult<S> = ValidatorResult.NoResult()
    override fun validate(stringToValidate: String): ValidatorResult<S> {
        validatorResult = when {
            minLength != null && stringToValidate.count() < minLength ->
                ValidatorResult.Failure(message(true, false))
            maxLength != null && stringToValidate.count() > maxLength ->
                ValidatorResult.Failure(message(false, true))
            else -> ValidatorResult.Success()
        }
        return validatorResult
    }
}