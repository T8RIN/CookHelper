package ru.tech.cookhelper.presentation.matched_recipes.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.cookhelper.core.onEmpty
import ru.tech.cookhelper.core.onError
import ru.tech.cookhelper.core.onSuccess
import ru.tech.cookhelper.domain.use_case.GetMatchedRecipesUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.matched_recipes.components.MatchedRecipeState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.getUIText
import javax.inject.Inject

@HiltViewModel
class MatchedRecipesViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    getMatchedRecipesUseCase: GetMatchedRecipesUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _user: MutableState<UserState> = mutableStateOf(UserState())
    val userState: UserState by _user

    private val _matchedRecipesState: MutableState<MatchedRecipeState> =
        mutableStateOf(MatchedRecipeState())
    val matchedRecipesState: MatchedRecipeState by _matchedRecipesState


    init {
        getUserUseCase().onEach {
            _matchedRecipesState.update { MatchedRecipeState(isLoading = true) }
            _user.update { UserState(it) }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            delay(500)
            getMatchedRecipesUseCase(userState.token)
                .onSuccess {
                    _matchedRecipesState.update { MatchedRecipeState(recipes = this@onSuccess) }
                }.onEmpty {
                    sendEvent(
                        Event.ShowToast(
                            getUIText(),
                            Icons.Rounded.ErrorOutline
                        )
                    )
                    _matchedRecipesState.update { MatchedRecipeState() }
                }.onError {
                    sendEvent(
                        Event.ShowToast(
                            UIText.UIText(this),
                            Icons.Rounded.ErrorOutline
                        )
                    )
                    _matchedRecipesState.update { MatchedRecipeState() }
                }
        }
    }

}