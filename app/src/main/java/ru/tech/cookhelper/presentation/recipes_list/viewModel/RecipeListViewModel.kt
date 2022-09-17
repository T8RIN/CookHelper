package ru.tech.cookhelper.presentation.recipes_list.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.use_case.get_recipes_list.GetRecipeListUseCase
import ru.tech.cookhelper.presentation.recipes_list.components.RecipeState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _recipeState = mutableStateOf(RecipeState())
    val recipeState: RecipeState by _recipeState

    init {
        getRecipeListUseCase().onEach { result ->
            when (result) {
                is Action.Success -> {
                    _recipeState.update { RecipeState(recipeList = result.data) }
                }
                is Action.Error -> {
                    _recipeState.update { copy(isLoading = false) }
                    sendEvent(Event.ShowToast(UIText.DynamicString(result.message ?: "")))
                }
                is Action.Loading -> {
                    _recipeState.update { RecipeState(isLoading = true) }
                }
                is Action.Empty -> TODO()
            }
        }.launchIn(viewModelScope)
    }

}