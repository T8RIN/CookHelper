package ru.tech.cookhelper.presentation.topic_creation.viewModel

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
import ru.tech.cookhelper.domain.use_case.create_topic.CreateTopicUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.presentation.topic_creation.components.TopicCreationState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.getUIText
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TopicCreationViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val createTopicUseCase: CreateTopicUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: User? by _user

    private val _topicCreationState: MutableState<TopicCreationState> =
        mutableStateOf(TopicCreationState())
    val topicCreationState by _topicCreationState

    init {
        getUserUseCase()
            .onEach { _user.update { it } }
            .launchIn(viewModelScope)
    }

    fun createTopic(
        content: String,
        label: String,
        tags: List<String>,
        files: List<Pair<File?, String>>
    ) {
        //TODO: Create cancel feature
        createTopicUseCase(user?.token ?: "", label, content, files, tags)
            .onSuccess {
                _topicCreationState.update { TopicCreationState(topic = this@onSuccess) }
            }.onLoading {
                _topicCreationState.update { TopicCreationState(isLoading = true) }
            }.onEmpty {
                _topicCreationState.update { TopicCreationState(isLoading = false) }
                sendEvent(
                    Event.ShowToast(
                        getUIText(),
                        Icons.Outlined.ErrorOutline
                    )
                )
            }.onError {
                _topicCreationState.update { TopicCreationState(isLoading = false) }
                sendEvent(
                    Event.ShowToast(
                        UIText.Companion.UIText(this),
                        Icons.Outlined.ErrorOutline
                    )
                )
            }.launchIn(viewModelScope)
    }

}