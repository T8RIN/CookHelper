package ru.tech.cookhelper.presentation.login_screen.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.*
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.cache_user.CacheUserUseCase
import ru.tech.cookhelper.domain.use_case.log_in.LoginUseCase
import ru.tech.cookhelper.presentation.authentication.components.getMessage
import ru.tech.cookhelper.presentation.login_screen.components.LoginState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val cacheUserUseCase: CacheUserUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _loginState: MutableState<LoginState> = mutableStateOf(LoginState())
    val loginState: LoginState by _loginState

    fun logInWith(login: String, password: String) {
        loginUseCase(login, password)
            .bindTo(_loginState)
            .onLoading { it.update { LoginState(isLoading = true) } }
            .onEmpty {
                it.update { LoginState() }
                sendEvent(
                    Event.ShowToast(
                        UIText.StringResource(getMessage()),
                        Icons.Rounded.ErrorOutline
                    )
                )
            }
            .onError {
                it.update { LoginState() }
                sendEvent(
                    Event.ShowToast(
                        UIText.DynamicString(this),
                        Icons.Rounded.ErrorOutline
                    )
                )
            }
            .onSuccess {
                this?.apply {
                    sendEvent(
                        Event.SendData(
                            "email" to email,
                            "name" to name,
                            "token" to token
                        )
                    )
                    if (!verified) sendEvent(
                        Event.NavigateTo(Screen.Authentication.Confirmation)
                    )
                    else {
                        sendEvent(
                            Event.ShowToast(
                                UIText.StringResource(
                                    R.string.welcome_user,
                                    name
                                ), Icons.Outlined.Face
                            )
                        )
                        cacheUser(this)
                    }
                }
                it.update { LoginState(user = this@onSuccess) }

            }.launchIn(viewModelScope)
    }

    private fun cacheUser(user: User) = viewModelScope.launch { cacheUserUseCase(user) }

}