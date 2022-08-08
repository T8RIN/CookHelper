package ru.tech.cookhelper.presentation.post_creation.viewModel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import javax.inject.Inject

@HiltViewModel
class PostCreationViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase
) : ViewModel() {

    fun createPost(content: String, label: String, imageUri: Uri) {
        TODO("Not yet implemented")
    }

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    init {
        getUserUseCase().onEach {
            _user.value = it
        }.launchIn(viewModelScope)
    }

}