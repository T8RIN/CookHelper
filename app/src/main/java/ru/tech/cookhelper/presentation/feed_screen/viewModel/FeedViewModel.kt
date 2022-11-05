package ru.tech.cookhelper.presentation.feed_screen.viewModel

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
import ru.tech.cookhelper.domain.use_case.get_feed.GetFeedUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.stop_awaiting_feed.StopAwaitingFeedUseCase
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.feed_screen.components.FeedState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.updateIf
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.getUIText
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
            .onEmpty {
                _feedState.update { copy(isLoading = false) }
                sendEvent(Event.ShowToast(getUIText()))
            }
            .onError {
                _feedState.update { copy(isLoading = false) }
                sendEvent(Event.ShowToast(UIText(this)))
            }
            .onLoading {
                _feedState.updateIf(
                    predicate = { this.data.isEmpty() }
                ) { copy(isLoading = true) }
            }
            .onSuccess {
                _feedState.update {
                    copy(
                        data = this@onSuccess + this.data,
                        isLoading = false
                    )
                }
            }.launchIn(viewModelScope)

    }

    override fun onCleared() {
        super.onCleared()
        stopAwaitingFeedUseCase()
    }

}