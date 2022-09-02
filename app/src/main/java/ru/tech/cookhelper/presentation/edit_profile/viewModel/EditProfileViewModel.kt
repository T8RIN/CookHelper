package ru.tech.cookhelper.presentation.edit_profile.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    fun setTemp(
        name: String = nameAndSurname.first,
        surname: String = nameAndSurname.second,
        nickname: String = nickAndEmail.first,
        email: String = nickAndEmail.second
    ) {
        _nameAndSurname.value = name to surname
        _nickAndEmail.value = nickname to email
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
