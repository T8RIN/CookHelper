package ru.tech.cookhelper.presentation.registration_screen.viewModel

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.use_case.check_email.CheckEmailForAvailabilityUseCase
import ru.tech.cookhelper.domain.use_case.check_login.CheckLoginForAvailabilityUseCase
import ru.tech.cookhelper.domain.use_case.registration.RegistrationUseCase
import ru.tech.cookhelper.domain.utils.text.ChainTextValidator
import ru.tech.cookhelper.domain.utils.text.TextValidator
import ru.tech.cookhelper.domain.utils.text.ValidatorResult
import ru.tech.cookhelper.domain.utils.text.validators.EmailTextValidator
import ru.tech.cookhelper.domain.utils.text.validators.HasNumberTextValidator
import ru.tech.cookhelper.domain.utils.text.validators.LengthTextValidator
import ru.tech.cookhelper.domain.utils.text.validators.NonEmptyTextValidator
import ru.tech.cookhelper.presentation.authentication.components.getMessage
import ru.tech.cookhelper.presentation.registration_screen.components.CheckEmailState
import ru.tech.cookhelper.presentation.registration_screen.components.CheckLoginState
import ru.tech.cookhelper.presentation.registration_screen.components.RegistrationState
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val checkLoginForAvailabilityUseCase: CheckLoginForAvailabilityUseCase,
    private val checkEmailForAvailabilityUseCase: CheckEmailForAvailabilityUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private var checkLoginJob: Job? = null
    private var checkEmailJob: Job? = null

    private val passwordValidator: TextValidator<UIText> = ChainTextValidator(
        NonEmptyTextValidator(UIText(R.string.required_field)),
        HasNumberTextValidator(UIText(R.string.password_must_contain_one_number)),
        LengthTextValidator(
            minLength = 8,
            message = UIText(R.string.password_too_short)
        )
    )

    private val emailValidator: TextValidator<UIText> = ChainTextValidator(
        NonEmptyTextValidator(UIText(R.string.required_field)),
        EmailTextValidator(UIText(R.string.bad_email), Patterns.EMAIL_ADDRESS)
    )

    private val nicknameValidator: TextValidator<UIText> = NonEmptyTextValidator(
        UIText(R.string.required_field)
    )

    private val _registrationState: MutableState<RegistrationState> =
        mutableStateOf(RegistrationState())
    val registrationState: RegistrationState by _registrationState

    private val _loginState: MutableState<CheckLoginState> =
        mutableStateOf(CheckLoginState())
    val loginState: CheckLoginState by _loginState

    private val _emailState: MutableState<CheckEmailState> =
        mutableStateOf(CheckEmailState())
    val emailState: CheckEmailState by _emailState


    fun validateLogin(login: String) {
        val result = nicknameValidator.validate(login)
        _loginState.value = CheckLoginState(isValid = result is ValidatorResult.Success)
        if (result is ValidatorResult.Error) _loginState.value =
            CheckLoginState(error = result.message)

        if (loginState.isValid) checkLoginForAvailability(login)
    }

    private fun checkLoginForAvailability(login: String) {
        checkLoginJob?.cancel()
        checkLoginJob = viewModelScope.launch {
            delay(500)
            _loginState.value = _loginState.value.copy(isLoading = true)
            when (val action = checkLoginForAvailabilityUseCase(login)) {
                is Action.Empty -> {
                    _loginState.value = CheckLoginState(
                        isValid = false,
                        error = UIText(action.status.getMessage())
                    )
                }
                is Action.Error -> {
                    _loginState.value = CheckLoginState(
                        isValid = false,
                        error = UIText(R.string.nickname_rejected)
                    )
                }
                is Action.Success -> {
                    if (loginState.isValid) _loginState.value =
                        CheckLoginState(isValid = true)
                    else _loginState.value = _loginState.value.copy(isLoading = false)
                }
                else -> {}
            }
        }
    }

    fun validateEmail(email: String) {
        val result = emailValidator.validate(email)
        _emailState.value = CheckEmailState(isValid = result is ValidatorResult.Success)
        if (result is ValidatorResult.Error) _emailState.value =
            CheckEmailState(error = result.message)

        if (emailState.isValid) checkEmailForAvailability(email)
    }

    private fun checkEmailForAvailability(email: String) {
        checkEmailJob?.cancel()
        checkEmailJob = viewModelScope.launch {
            delay(500)
            _emailState.value = _emailState.value.copy(isLoading = true)
            when (val action = checkEmailForAvailabilityUseCase(email)) {
                is Action.Empty -> {
                    _emailState.value = CheckEmailState(
                        isValid = false,
                        error = UIText(action.status.getMessage())
                    )
                }
                is Action.Error -> {
                    _emailState.value = CheckEmailState(
                        isValid = false,
                        error = UIText(R.string.email_rejected)
                    )
                }
                is Action.Success -> {
                    if (emailState.isValid) _emailState.value =
                        CheckEmailState(isValid = true)
                    else _emailState.value = _emailState.value.copy(isLoading = false)
                }
                else -> {}
            }
        }
    }

    fun registerWith(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ) {
        registrationUseCase(name, surname, nickname, email, password).onEach { result ->
            when (result) {
                is Action.Loading -> _registrationState.value = RegistrationState(isLoading = true)
                is Action.Error -> {
                    _registrationState.value = RegistrationState()
                    sendEvent(Event.ShowToast(UIText.DynamicString(result.message.toString())))
                }
                is Action.Success -> {
                    result.data?.let {
                        sendEvent(
                            Event.SendData(
                                mapOf(
                                    "email" to it.email,
                                    "name" to it.name,
                                    "token" to it.token
                                )
                            )
                        )
                        if (!it.verified) sendEvent(
                            Event.NavigateTo(Screen.Authentication.Confirmation)
                        )
                    }
                    _registrationState.value = RegistrationState(user = result.data)
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private val _isPasswordValid: MutableState<Boolean> = mutableStateOf(false)
    val isPasswordValid: Boolean by _isPasswordValid

    private val _passwordValidationError: MutableState<UIText> =
        mutableStateOf(UIText.StringResource(R.string.required_field))
    val passwordValidationError: UIText by _passwordValidationError

    fun validatePassword(password: String) {
        val result = passwordValidator.validate(password)
        _isPasswordValid.value = result is ValidatorResult.Success
        if (result is ValidatorResult.Error) _passwordValidationError.value = result.message
    }

}