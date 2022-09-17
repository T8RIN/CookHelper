package ru.tech.cookhelper.presentation.edit_profile.viewModel

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
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.utils.text.ChainTextValidator
import ru.tech.cookhelper.domain.utils.text.TextValidator
import ru.tech.cookhelper.domain.utils.text.ValidatorResult
import ru.tech.cookhelper.domain.utils.text.validators.EmailTextValidator
import ru.tech.cookhelper.domain.utils.text.validators.NonEmptyTextValidator
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.authentication.components.getMessage
import ru.tech.cookhelper.presentation.registration_screen.components.CheckEmailState
import ru.tech.cookhelper.presentation.registration_screen.components.CheckLoginState
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val checkLoginForAvailabilityUseCase: CheckLoginForAvailabilityUseCase,
    private val checkEmailForAvailabilityUseCase: CheckEmailForAvailabilityUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private var checkLoginJob: Job? = null
    private var checkEmailJob: Job? = null

    private val _loginState: MutableState<CheckLoginState> =
        mutableStateOf(CheckLoginState())
    val loginState: CheckLoginState by _loginState

    private val _emailState: MutableState<CheckEmailState> =
        mutableStateOf(CheckEmailState())
    val emailState: CheckEmailState by _emailState

    private val emailValidator: TextValidator<UIText> = ChainTextValidator(
        NonEmptyTextValidator(UIText(R.string.required_field)),
        EmailTextValidator(UIText(R.string.bad_email), Patterns.EMAIL_ADDRESS)
    )

    private val nicknameValidator: TextValidator<UIText> = NonEmptyTextValidator(
        UIText(R.string.required_field)
    )

    fun setTemp(
        name: String = nameAndSurname.first,
        surname: String = nameAndSurname.second,
        nickname: String = nickAndEmail.first,
        email: String = nickAndEmail.second
    ) {
        _nameAndSurname.value = name to surname
        _nickAndEmail.value = nickname to email
    }

    fun validateLogin(login: String) {
        val result = nicknameValidator.validate(login)
        _loginState.value = CheckLoginState(isValid = result is ValidatorResult.Success)
        if (result is ValidatorResult.Error) _loginState.value =
            CheckLoginState(error = result.message)

        if (loginState.isValid) checkLoginForAvailability(login)
    }

    private fun checkLoginForAvailability(login: String) {
        checkLoginJob?.cancel()
        if (login != userState.user?.nickname) {
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
        if (email != userState.user?.email) {
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
    }

    fun savePersonalInfo() {
        /*TODO: save*/
    }

    fun saveAccountInfo(password: String) {
        /*TODO: save*/
    }

    fun savePassword(password: String, oldPassword: String) {
        /*TODO: save*/
    }

    private val _userState: MutableState<UserState> = mutableStateOf(UserState())
    val userState: UserState by _userState

    private val _nameAndSurname: MutableState<Pair<String, String>> = mutableStateOf("" to "")
    val nameAndSurname by _nameAndSurname

    private val _nickAndEmail: MutableState<Pair<String, String>> = mutableStateOf("" to "")
    val nickAndEmail by _nickAndEmail

    init {
        getUserUseCase().onEach { user ->
            user?.let {
                _userState.value = UserState(it, it.token)
                _nameAndSurname.value = it.name to it.surname
                _nickAndEmail.value = it.nickname to it.email
            }
        }.launchIn(viewModelScope)
    }

}
