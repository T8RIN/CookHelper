package ru.tech.cookhelper.presentation.feed.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.use_case.get_feed.GetFeedUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.stop_awaiting_feed.StopAwaitingFeedUseCase
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.feed.components.FeedState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    getFeedUseCase: GetFeedUseCase,
    private val stopAwaitingFeedUseCase: StopAwaitingFeedUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _user: MutableState<UserState> = mutableStateOf(UserState())
    val user: UserState by _user

    private val _feedState: MutableState<FeedState> = mutableStateOf(FeedState())
    val feedState: FeedState by _feedState

    init {
        getUserUseCase().onEach {
            _user.update { UserState(it) }
        }.launchIn(viewModelScope)

        getFeedUseCase(user.token)
            .onEach { action ->
                when (action) {
                    is Action.Empty -> _feedState.update { copy(isLoading = false) }
                    is Action.Error -> {
                        _feedState.update { copy(isLoading = false) }
                        sendEvent(
                            Event.ShowToast(
                                //TODO: Move this to repo layer
                                if (!(action.message ?: "").contains("Unable to resolve host")
                                ) UIText.DynamicString(action.message) else UIText.StringResource(R.string.no_connection)
                            )
                        )
                    }
                    is Action.Loading -> _feedState.update {
                        if (this.data.isEmpty()) copy(isLoading = true)
                        else this
                    }
                    is Action.Success -> _feedState.update {
                        copy(
                            data = (action.data ?: emptyList()) + this.data,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)

    }

    override fun onCleared() {
        super.onCleared()
        stopAwaitingFeedUseCase()
    }

}