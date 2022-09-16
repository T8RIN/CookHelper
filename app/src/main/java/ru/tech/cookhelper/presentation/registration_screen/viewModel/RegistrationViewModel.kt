package ru.tech.cookhelper.presentation.registration_screen.viewModel

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
import ru.tech.cookhelper.domain.use_case.check_login.CheckLoginOrEmailForAvailabilityUseCase
import ru.tech.cookhelper.domain.use_case.registration.RegistrationUseCase
import ru.tech.cookhelper.domain.utils.text.ChainTextValidator
import ru.tech.cookhelper.domain.utils.text.TextValidator
import ru.tech.cookhelper.domain.utils.text.ValidatorResult
import ru.tech.cookhelper.domain.utils.text.validators.HasNumberTextValidator
import ru.tech.cookhelper.domain.utils.text.validators.LengthTextValidator
import ru.tech.cookhelper.domain.utils.text.validators.NonEmptyTextValidator
import ru.tech.cookhelper.presentation.authentication.components.getMessage
import ru.tech.cookhelper.presentation.registration_screen.components.CheckLoginOrEmailState
import ru.tech.cookhelper.presentation.registration_screen.components.RegistrationState
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val checkLoginOrEmailForAvailabilityUseCase: CheckLoginOrEmailForAvailabilityUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private var checkLoginJob: Job? = null
    private var checkEmailJob: Job? = null

    private val passwordValidator: TextValidator<UIText> = ChainTextValidator(
        NonEmptyTextValidator(UIText.StringResource(R.string.required_field)),
        HasNumberTextValidator(UIText.StringResource(R.string.password_must_contain_one_number)),
        LengthTextValidator(
            minLength = 9,
            message = UIText.StringResource(R.string.password_too_short)
        )
    )

    private val _registrationState: MutableState<RegistrationState> =
        mutableStateOf(RegistrationState())
    val registrationState: RegistrationState by _registrationState

    private val _checkLoginState: MutableState<CheckLoginOrEmailState> =
        mutableStateOf(CheckLoginOrEmailState())
    val checkLoginState: CheckLoginOrEmailState by _checkLoginState

    private val _checkEmailState: MutableState<CheckLoginOrEmailState> =
        mutableStateOf(CheckLoginOrEmailState())
    val checkEmailState: CheckLoginOrEmailState by _checkEmailState

    fun checkLoginForAvailability(login: String) {
        checkLoginJob?.cancel()
        checkLoginJob = viewModelScope.launch {
            delay(500)
            _checkLoginState.value = CheckLoginOrEmailState(isLoading = true)
            when (val result = checkLoginOrEmailForAvailabilityUseCase(login)) {
                is Action.Empty -> _checkLoginState.value =
                    CheckLoginOrEmailState(error = UIText.StringResource(result.status.getMessage()))
                is Action.Error -> _checkLoginState.value =
                    CheckLoginOrEmailState(error = UIText.DynamicString(result.message.toString()))
                is Action.Success -> {
                    _checkLoginState.value = CheckLoginOrEmailState(error = UIText.Empty())
                }
                else -> {}
            }
        }
    }

    fun checkEmailForAvailability(email: String) {
        checkEmailJob?.cancel()
        checkEmailJob = viewModelScope.launch {
            delay(500)
            _checkEmailState.value = CheckLoginOrEmailState(isLoading = true)
            when (val result = checkLoginOrEmailForAvailabilityUseCase(email)) {
                is Action.Empty -> _checkEmailState.value =
                    CheckLoginOrEmailState(error = UIText.StringResource(result.status.getMessage()))
                is Action.Error -> _checkEmailState.value =
                    CheckLoginOrEmailState(error = UIText.DynamicString(result.message.toString()))
                is Action.Success -> {
                    _checkEmailState.value = CheckLoginOrEmailState(error = UIText.Empty())
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

    private val _passwordValidationError: MutableState<UIText> = mutableStateOf(UIText.StringResource(R.string.required_field))
    val passwordValidationError: UIText by _passwordValidationError

    fun validatePassword(password: String) {
        val result = passwordValidator.validate(password)
        _isPasswordValid.value = result is ValidatorResult.Success
        if (result is ValidatorResult.Error) _passwordValidationError.value = result.message
    }

}