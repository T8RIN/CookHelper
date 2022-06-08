package ru.tech.cookhelper.presentation.authentication.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tech.cookhelper.domain.use_case.get_favourites.GetFavouriteDishesUseCase
import ru.tech.cookhelper.presentation.authentication.components.AuthState
import ru.tech.cookhelper.presentation.authentication.components.CodeState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: GetFavouriteDishesUseCase
) : ViewModel() {

    var job: Job? = null

    var codeTimeout by mutableStateOf(60)

    var currentEmail = ""
    var currentNick = ""

    private val _codeState = mutableStateOf(CodeState())
    val codeState: State<CodeState> = _codeState

    fun forgotPassword() {
        _authState.value = AuthState.RestorePassword
    }

    fun logInWith(login: String, password: String) {
        //TODO: logic
    }

    fun signUp() {
        _authState.value = AuthState.Registration
    }

    fun logIn() {
        _authState.value = AuthState.Login
    }

    fun registerWith(nick: String, email: String, password: String) {
        currentEmail = email
        currentNick = nick

        _authState.value = AuthState.ConfirmEmail

        job?.cancel()
        job = viewModelScope.launch {
            while(codeTimeout != 0) {
                delay(1000)
                codeTimeout--
            }
            job?.cancel()
        }
    }

    fun checkCode(code: String) {
        //TODO: logic

        _authState.value = AuthState.Login
    }

    fun askForCode() {

        codeTimeout = 60
    }

    private val _authState: MutableState<AuthState> = mutableStateOf(AuthState.Login)
    val authState: State<AuthState> = _authState


}