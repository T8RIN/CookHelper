package ru.tech.cookhelper.domain.utils.text.validators

import ru.tech.cookhelper.domain.utils.text.TextValidator
import ru.tech.cookhelper.domain.utils.text.ValidatorResult
import java.util.regex.Pattern

class EmailTextValidator<S>(
    private val message: S,
    private val pattern: Pattern
) : TextValidator<S> {
    override var validatorResult: ValidatorResult<S> = ValidatorResult.NoResult()
    override fun validate(stringToValidate: String): ValidatorResult<S> {
        validatorResult = when {
            pattern.matcher(stringToValidate).matches() -> ValidatorResult.Success()
            else -> ValidatorResult.Error(message)
        }
        return validatorResult
    }
}