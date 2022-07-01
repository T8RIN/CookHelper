package ru.tech.cookhelper.presentation.authentication.viewModel

import androidx.compose.runtime.*
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
import ru.tech.cookhelper.data.remote.api.auth.toUser
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.cache_user.CacheUserUseCase
import ru.tech.cookhelper.domain.use_case.check_code.CheckCodeUseCase
import ru.tech.cookhelper.domain.use_case.check_login.CheckLoginOrEmailForAvailability
import ru.tech.cookhelper.domain.use_case.login.LoginUseCase
import ru.tech.cookhelper.domain.use_case.registration.RegistrationUseCase
import ru.tech.cookhelper.domain.use_case.request_code.RequestCodeUseCase
import ru.tech.cookhelper.domain.use_case.restore_password.ApplyPasswordByCodeUseCase
import ru.tech.cookhelper.domain.use_case.restore_password.SendRestoreCodeUseCase
import ru.tech.cookhelper.presentation.authentication.components.*
import ru.tech.cookhelper.presentation.ui.utils.UIText
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val checkCodeUseCase: CheckCodeUseCase,
    private val requestCodeUseCase: RequestCodeUseCase,
    private val cacheUserUseCase: CacheUserUseCase,
    private val sendRestoreCodeUseCase: SendRestoreCodeUseCase,
    private val applyPasswordByCodeUseCase: ApplyPasswordByCodeUseCase,
    private val checkLoginOrEmailForAvailability: CheckLoginOrEmailForAvailability
) : ViewModel() {

    private var timerJob: Job? = null
    private var checkLoginJob: Job? = null
    private var checkEmailJob: Job? = null

    var codeTimeout by mutableStateOf(60)

    var currentEmail = ""
    var currentName = ""
    private var restoreLogin = ""
    private var currentToken = ""

    private var previousState by mutableStateOf(AuthState.Login)

    private val _authState: MutableState<AuthState> = mutableStateOf(AuthState.Login)
    val authState: State<AuthState> = _authState

    private val _codeState = mutableStateOf(CodeState())
    val codeState: State<CodeState> = _codeState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _registrationState = mutableStateOf(RegistrationState())
    val registrationState: State<RegistrationState> = _registrationState

    private val _restorePasswordState = mutableStateOf(RestorePasswordState())
    val restorePasswordState: State<RestorePasswordState> = _restorePasswordState

    private val _restorePasswordCodeState = mutableStateOf(CodeState())
    val restorePasswordCodeState: State<CodeState> = _restorePasswordCodeState

    private val _checkLoginState = mutableStateOf(CheckLoginOrEmailState())
    val checkLoginState: State<CheckLoginOrEmailState> = _checkLoginState

    private val _checkEmailState = mutableStateOf(CheckLoginOrEmailState())
    val checkEmailState: State<CheckLoginOrEmailState> = _checkEmailState

    fun openPasswordRestore() {
        _restorePasswordState.value = RestorePasswordState(state = RestoreState.Login)
        previousState = _authState.value
        _authState.value = AuthState.RestorePassword
    }

    fun logInWith(login: String, password: String) {
        loginUseCase(login, password).onEach { result ->
            when (result) {
                is Action.Loading -> _loginState.value = LoginState(isLoading = true)
                is Action.Empty -> _loginState.value =
                    LoginState(error = UIText.StringResource(result.status.getMessage()))
                is Action.Error -> _loginState.value =
                    LoginState(error = UIText.DynamicString(result.message.toString()))
                is Action.Success -> {
                    result.data?.user?.let {
                        currentEmail = it.email
                        currentName = it.name
                        currentToken = it.token
                        if (!it.verified) openEmailConfirmation()
                        else cacheUser(it.toUser())
                    }
                    _loginState.value = LoginState(user = result.data?.user?.toUser())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun openRegistration() {
        previousState = _authState.value
        _authState.value = AuthState.Registration
        resetCodeState()
    }

    fun openLogin() {
        previousState = _authState.value
        _authState.value = AuthState.Login
    }

    private fun resetCodeState() {
        _codeState.value = CodeState()
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
                is Action.Error -> _registrationState.value =
                    RegistrationState(error = UIText.DynamicString(result.message.toString()))
                is Action.Success -> {
                    result.data?.user?.let {
                        currentEmail = it.email
                        currentName = it.name
                        currentToken = it.token
                        if (!it.verified) openEmailConfirmation()
                    }
                    _registrationState.value = RegistrationState(user = result.data?.user?.toUser())
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun reloadTimer() {
        codeTimeout = 60

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (codeTimeout != 0) {
                delay(1000)
                codeTimeout--
            }
            timerJob?.cancel()
        }
    }

    fun checkVerificationCode(code: String) {
        checkCodeUseCase(code, currentToken).onEach { result ->
            when (result) {
                is Action.Loading -> _codeState.value = CodeState(isLoading = true)
                is Action.Error -> _codeState.value =
                    CodeState(error = UIText.DynamicString(result.message.toString()))
                is Action.Success -> {
                    result.data?.user?.let {
                        _codeState.value = CodeState(matched = true)
                        cacheUser(it.toUser())
                    }
                }
                is Action.Empty -> _codeState.value =
                    CodeState(error = UIText.StringResource(R.string.wrong_code))
            }
        }.launchIn(viewModelScope)
    }

    private fun cacheUser(user: User) {
        viewModelScope.launch { cacheUserUseCase(user) }
    }

    fun askForVerificationCode() {
        viewModelScope.launch {
            val result = requestCodeUseCase(currentToken)
            if (result.isFailure) {
                _codeState.value = _codeState.value.copy(
                    error = UIText.DynamicString(result.exceptionOrNull()?.message.toString())
                )
            } else reloadTimer()
        }
    }

    fun restorePasswordBy(login: String) {
        viewModelScope.launch {
            _restorePasswordState.value = _restorePasswordState.value.copy(isLoading = true)
            val result = sendRestoreCodeUseCase(login)
            if (result.isFailure) {
                _restorePasswordState.value = RestorePasswordState(
                    error = UIText.DynamicString(result.exceptionOrNull()?.message.toString())
                )
            } else {
                result.getOrNull()?.let {
                    when (it.status) {
                        100 -> {
                            _restorePasswordState.value =
                                RestorePasswordState(state = RestoreState.Password)
                            restoreLogin = login
                        }
                        101 -> _restorePasswordState.value =
                            RestorePasswordState(error = UIText.StringResource(R.string.user_not_found))
                        else -> _restorePasswordState.value =
                            RestorePasswordState(error = UIText.DynamicString("${it.status} ${it.message}"))
                    }
                }
            }
        }
    }

    private fun openEmailConfirmation() {
        resetCodeState()
        previousState = _authState.value
        _authState.value = AuthState.ConfirmEmail
        reloadTimer()
    }

    fun resetState() {
        _codeState.value = _codeState.value.copy(matched = false)
        _registrationState.value = _registrationState.value.copy(error = UIText.empty())
        _loginState.value = LoginState()
        _restorePasswordState.value =
            _restorePasswordState.value.copy(found = false, error = UIText.empty())
        viewModelScope.launch { delay(800); _restorePasswordCodeState.value = CodeState() }
    }

    fun applyPasswordByCode(code: String, password: String) {
        applyPasswordByCodeUseCase(restoreLogin, code, password).onEach { result ->
            when (result) {
                is Action.Loading -> _restorePasswordState.value =
                    RestorePasswordState(
                        isLoading = true,
                        state = RestoreState.Password
                    )
                is Action.Error -> {
                    _restorePasswordState.value =
                        RestorePasswordState(
                            error = UIText.DynamicString(result.message.toString()),
                            state = RestoreState.Password
                        )
                    _restorePasswordCodeState.value =
                        CodeState(error = UIText.DynamicString(result.message.toString()))
                }
                is Action.Success -> {
                    result.data?.user?.let {
                        _restorePasswordState.value = RestorePasswordState(
                            user = it.toUser(),
                            state = RestoreState.Password
                        )
                        _restorePasswordCodeState.value = CodeState(matched = true)
                        openLogin()
                    }
                }
                is Action.Empty -> {
                    _restorePasswordState.value = RestorePasswordState(
                        error = UIText.StringResource(R.string.wrong_code),
                        state = RestoreState.Password
                    )
                    _restorePasswordCodeState.value =
                        CodeState(error = UIText.StringResource(R.string.wrong_code))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun checkLoginForAvailability(login: String) {
        checkLoginJob?.cancel()
        checkLoginJob = viewModelScope.launch {
            delay(500)
            _checkLoginState.value = CheckLoginOrEmailState(isLoading = true)
            when (val result = checkLoginOrEmailForAvailability(login)) {
                is Action.Empty -> _checkLoginState.value =
                    CheckLoginOrEmailState(error = UIText.StringResource(result.status.getMessage()))
                is Action.Error -> _checkLoginState.value =
                    CheckLoginOrEmailState(error = UIText.DynamicString(result.message.toString()))
                is Action.Success -> {
                    _checkLoginState.value = CheckLoginOrEmailState(error = UIText.empty())
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
            when (val result = checkLoginOrEmailForAvailability(email)) {
                is Action.Empty -> _checkEmailState.value =
                    CheckLoginOrEmailState(error = UIText.StringResource(result.status.getMessage()))
                is Action.Error -> _checkEmailState.value =
                    CheckLoginOrEmailState(error = UIText.DynamicString(result.message.toString()))
                is Action.Success -> {
                    _checkEmailState.value = CheckLoginOrEmailState(error = UIText.empty())
                }
                else -> {}
            }
        }
    }

    fun goBack() {
        _authState.value = previousState
    }

    fun goBackPasswordRestore() {
        _restorePasswordState.value = _restorePasswordState.value.copy(state = RestoreState.Login)
    }

}

private fun Int?.getMessage(): Int {
    if (this == null) return R.string.unexpected_error

    return when (this) {
        101 -> R.string.user_not_found
        102 -> R.string.wrong_password_or_nick
        103 -> TODO()
        104 -> TODO()
        105 -> TODO()
        106 -> TODO()
        107 -> TODO()
        108 -> TODO()
        109 -> TODO()
        else -> R.string.unexpected_error
    }
}
