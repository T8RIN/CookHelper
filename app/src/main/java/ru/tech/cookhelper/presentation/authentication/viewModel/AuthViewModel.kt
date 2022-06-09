package ru.tech.cookhelper.presentation.authentication.viewModel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.data.remote.api.auth.start
import ru.tech.cookhelper.domain.use_case.login.LoginUseCase
import ru.tech.cookhelper.presentation.authentication.components.AuthState
import ru.tech.cookhelper.presentation.authentication.components.CodeState
import ru.tech.cookhelper.presentation.authentication.components.LoginState
import ru.tech.cookhelper.presentation.ui.utils.UIText
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private var timerJob: Job? = null
    private var codeCheckingJob: Job? = null

    var codeTimeout by mutableStateOf(60)

    var currentEmail = ""
    var currentNick = ""

    private val _codeState = mutableStateOf(CodeState())
    val codeState: State<CodeState> = _codeState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun openPasswordRestore() {
        _authState.value = AuthState.RestorePassword
    }

    fun logInWith(login: String, password: String) {
        _loginState.value = LoginState(isLoading = true)

        loginUseCase(login, password).start(
            onSuccess = { _, response ->
                if(response.body()?.status == -1) _loginState.value = LoginState(error = UIText.StringResource(R.string.wrong_password_or_nick))
                else _loginState.value = LoginState(user = response.body()?.user)
            }, onError = { _, t ->
                _loginState.value = LoginState(error = UIText.DynamicString(t.message.toString()))
            }
        )
    }

    fun openRegistration() {
        _authState.value = AuthState.Registration

        resetCodeState()
    }

    fun openLogin() {
        _authState.value = AuthState.Login
    }

    private fun resetCodeState() {
        _codeState.value = CodeState()
    }

    fun registerWith(name: String, surname: String, nick: String, email: String, password: String) {
        currentEmail = email
        currentNick = nick

        resetCodeState()

        _authState.value = AuthState.ConfirmEmail

        reloadTimer()
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

    fun checkCode(code: String) {
        //TODO: logic


        _codeState.value = CodeState(isLoading = true)

        codeCheckingJob?.cancel()
        codeCheckingJob = viewModelScope.launch {
            delay(2000)
            _codeState.value = listOf(
                CodeState(error = UIText.StringResource(R.string.wrong_code)),
                CodeState(matched = true)
            ).shuffled()[0]

            if (_codeState.value.matched) openLogin()

            codeCheckingJob?.cancel()
        }
    }

    fun askForCode() {

        reloadTimer()
    }

    fun restorePasswordBy(email: String) {

        openLogin()
    }

    private val _authState: MutableState<AuthState> = mutableStateOf(AuthState.Login)
    val authState: State<AuthState> = _authState

}