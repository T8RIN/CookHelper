package ru.tech.cookhelper.presentation.dish_details.viewModel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.use_case.check_favourite.CheckFavouriteUseCase
import ru.tech.cookhelper.domain.use_case.get_dish_by_id.GetDishByIdUseCase
import ru.tech.cookhelper.presentation.dish_details.components.DishDetailsState
import ru.tech.cookhelper.presentation.ui.utils.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@ExperimentalMaterial3Api
@HiltViewModel
class DishDetailsViewModel @Inject constructor(
    getDishByIdUseCase: GetDishByIdUseCase,
    private val checkFavouriteUseCase: CheckFavouriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private var id = -1

    private val _dishState = mutableStateOf(DishDetailsState())
    val dishState: State<DishDetailsState> = _dishState

    private val _isFavorite = mutableStateOf(false)
    val isFavorite: State<Boolean> = _isFavorite

    init {
        id = savedStateHandle["id"]!!
        getDishByIdUseCase(id).onEach { result ->
            _isFavorite.value = checkFavouriteUseCase(id)

            when (result) {
                is Action.Success -> {
                    _dishState.value = DishDetailsState(dish = result.data)
                }
                is Action.Error -> {
                    _dishState.value = _dishState.value.copy(isLoading = false)
                    sendEvent(Event.ShowToast(UIText.DynamicString(result.message ?: "")))
                }
                is Action.Loading -> {
                    _dishState.value = DishDetailsState(isLoading = true)
                }
                is Action.Empty -> TODO()
            }
        }.launchIn(viewModelScope)
    }

}
