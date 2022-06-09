package ru.tech.cookhelper.presentation.authentication.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.use_case.get_favourites.GetFavouriteDishesUseCase
import ru.tech.cookhelper.presentation.authentication.components.AuthState
import ru.tech.cookhelper.presentation.authentication.components.CodeState
import ru.tech.cookhelper.presentation.ui.utils.UIText
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: GetFavouriteDishesUseCase
) : ViewModel() {

    private var timerJob: Job? = null
    private var codeCheckingJob: Job? = null

    var codeTimeout by mutableStateOf(60)

    var currentEmail = ""
    var currentNick = ""

    private val _codeState = mutableStateOf(CodeState())
    val codeState: State<CodeState> = _codeState

    fun openPasswordRestore() {
        _authState.value = AuthState.RestorePassword
    }

    fun logInWith(login: String, password: String) {
        //TODO: logic
    }

    fun openRegistration() {
        _authState.value = AuthState.Registration
    }

    fun openLogin() {
        _authState.value = AuthState.Login
    }

    fun registerWith(nick: String, email: String, password: String) {
        currentEmail = email
        currentNick = nick

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