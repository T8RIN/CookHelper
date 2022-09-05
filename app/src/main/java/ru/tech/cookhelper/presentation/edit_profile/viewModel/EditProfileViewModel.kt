package ru.tech.cookhelper.presentation.edit_profile.viewModel

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
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.use_case.check_login.CheckLoginOrEmailForAvailabilityUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.authentication.components.getMessage
import ru.tech.cookhelper.presentation.registration_screen.components.CheckLoginOrEmailState
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val checkLoginOrEmailForAvailabilityUseCase: CheckLoginOrEmailForAvailabilityUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private var checkLoginJob: Job? = null
    private var checkEmailJob: Job? = null

    private val _checkLoginState: MutableState<CheckLoginOrEmailState> =
        mutableStateOf(CheckLoginOrEmailState())
    val checkLoginState: CheckLoginOrEmailState by _checkLoginState

    private val _checkEmailState: MutableState<CheckLoginOrEmailState> =
        mutableStateOf(CheckLoginOrEmailState())
    val checkEmailState: CheckLoginOrEmailState by _checkEmailState

    fun setTemp(
        name: String = nameAndSurname.first,
        surname: String = nameAndSurname.second,
        nickname: String = nickAndEmail.first,
        email: String = nickAndEmail.second
    ) {
        _nameAndSurname.value = name to surname
        _nickAndEmail.value = nickname to email
    }

    fun checkLoginForAvailability(login: String) {
        checkLoginJob?.cancel()
        checkLoginJob = viewModelScope.launch {
            delay(500)
            if (login != _userState.value.user?.nickname) {
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
    }

    fun checkEmailForAvailability(email: String) {
        checkEmailJob?.cancel()
        checkEmailJob = viewModelScope.launch {
            delay(500)
            if (email != _userState.value.user?.email) {
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
