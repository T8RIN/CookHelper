package ru.tech.cookhelper.presentation.favourite_dishes.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.use_case.get_favourites.GetFavouriteDishesUseCase
import ru.tech.cookhelper.presentation.recipes_list.components.RecipeState
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class FavouriteListViewModel @Inject constructor(
    private val getFavouriteDishesUseCase: GetFavouriteDishesUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _favState = mutableStateOf(RecipeState())
    val favState: State<RecipeState> = _favState

    init {
        getFavouriteDishesUseCase().onEach { result ->
            when (result) {
                is Action.Success -> {
                    _favState.value = RecipeState(recipeList = result.data)
                }
                is Action.Error -> {
                    _favState.value = _favState.value.copy(isLoading = false)
                    sendEvent(Event.ShowToast(UIText.DynamicString(result.message ?: "")))
                }
                is Action.Loading -> {
                    _favState.value = RecipeState(isLoading = true)
                }
                is Action.Empty -> TODO()
            }
        }.launchIn(viewModelScope)
    }

}