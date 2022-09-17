package ru.tech.cookhelper.domain.utils.text

class ChainTextValidator<S>(
    private vararg val validators: TextValidator<S>
) : TextValidator<S> {
    override var validatorResult: ValidatorResult<S> = ValidatorResult.NoResult()
    override fun validate(stringToValidate: String): ValidatorResult<S> {
        validators.forEach { validator ->
            validatorResult = validator.validate(stringToValidate)
            if (validatorResult is ValidatorResult.Failure) return validatorResult
        }
        return validatorResult
    }
}
