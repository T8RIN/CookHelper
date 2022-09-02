package ru.tech.cookhelper.presentation.profile.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.log_out.LogoutUseCase
import ru.tech.cookhelper.presentation.app.components.UserState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _userState: MutableState<UserState> = mutableStateOf(UserState())
    val userState: UserState by _userState

    init {
        getUserUseCase().onEach { user ->
            user?.let { _userState.value = UserState(it, it.token) }
        }.launchIn(viewModelScope)
    }

    fun logOut() {
        viewModelScope.launch { logoutUseCase() }
    }

    fun addImage(imageUri: String) {
        //TODO: Send picked image to server
    }

    fun updateStatus(newStatus: String) {
        /*TODO: UpdateStatus*/
    }

    fun addAvatar(imageUri: String) {
        /*TODO: UpdateAvatar*/
    }

}