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
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.onEmpty
import ru.tech.cookhelper.core.onError
import ru.tech.cookhelper.core.onLoading
import ru.tech.cookhelper.core.onSuccess
import ru.tech.cookhelper.domain.use_case.cache_user.CacheUserUseCase
import ru.tech.cookhelper.domain.use_case.log_in.LoginUseCase
import ru.tech.cookhelper.presentation.login_screen.components.LoginState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.getMessage
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
            .onLoading {
                _loginState.update { LoginState(isLoading = true) }
            }
            .onEmpty {
                _loginState.update { LoginState() }
                sendEvent(
                    Event.ShowToast(
                        UIText.StringResource(getMessage()),
                        Icons.Rounded.ErrorOutline
                    )
                )
            }
            .onError {
                _loginState.update { LoginState() }
                sendEvent(
                    Event.ShowToast(
                        UIText.DynamicString(this),
                        Icons.Rounded.ErrorOutline
                    )
                )
            }
            .onSuccess {
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
                    cacheUserUseCase(this)
                }
                _loginState.update { LoginState(user = this@onSuccess) }
            }
            .launchIn(viewModelScope)
    }

}