package ru.tech.cookhelper.presentation.post_creation.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.onEmpty
import ru.tech.cookhelper.core.onError
import ru.tech.cookhelper.core.onLoading
import ru.tech.cookhelper.core.onSuccess
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.create_post.CreatePostUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.presentation.post_creation.components.PostCreationState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.getMessage
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostCreationViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val createPostUseCase: CreatePostUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: User? by _user

    private val _postCreationState: MutableState<PostCreationState> =
        mutableStateOf(PostCreationState())
    val postCreationState by _postCreationState

    init {
        getUserUseCase()
            .onEach { _user.update { it } }
            .launchIn(viewModelScope)
    }

    fun createPost(content: String, label: String, imageFile: File?) {
        //TODO: Create cancel feature
        createPostUseCase(user?.token ?: "", label, content, imageFile)
            .onSuccess {
                _postCreationState.update { PostCreationState(post = this@onSuccess) }
            }.onLoading {
                _postCreationState.update { PostCreationState(isLoading = true) }
            }.onEmpty {
                _postCreationState.update { PostCreationState(isLoading = false) }
                sendEvent(
                    Event.ShowToast(
                        UIText(getMessage()),
                        Icons.Outlined.ErrorOutline
                    )
                )
            }.onError {
                _postCreationState.update { PostCreationState(isLoading = false) }
                sendEvent(
                    Event.ShowToast(
                        UIText(this),
                        Icons.Outlined.ErrorOutline
                    )
                )
            }.launchIn(viewModelScope)
    }

}