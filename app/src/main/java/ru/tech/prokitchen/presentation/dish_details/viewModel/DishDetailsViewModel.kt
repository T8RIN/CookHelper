package ru.tech.prokitchen.presentation.dish_details.viewModel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.prokitchen.core.Action
import ru.tech.prokitchen.domain.use_case.check_favourite.CheckFavouriteUseCase
import ru.tech.prokitchen.domain.use_case.get_dish_by_id.GetDishByIdUseCase
import ru.tech.prokitchen.domain.use_case.update_favorite.UpdateFavDishUseCase
import ru.tech.prokitchen.presentation.dish_details.components.DishDetailsState
import javax.inject.Inject

@HiltViewModel
class DishDetailsViewModel @Inject constructor(
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val checkFavouriteUseCase: CheckFavouriteUseCase,
    private val updateFavDishUseCase: UpdateFavDishUseCase
) : ViewModel() {

    @ExperimentalMaterial3Api
    val scrollBehavior by mutableStateOf(TopAppBarDefaults.enterAlwaysScrollBehavior())

    private var id by mutableStateOf(-1)

    private val _dishState = mutableStateOf(DishDetailsState())
    val dishState: State<DishDetailsState> = _dishState

    private val _isFavorite = mutableStateOf(false)
    val isFavorite: State<Boolean> = _isFavorite

    private fun getDishById(id: Int) {
        getDishByIdUseCase(id).onEach { result ->
            _isFavorite.value = checkFavouriteUseCase(id)

            when (result) {
                is Action.Success -> {
                    _dishState.value = DishDetailsState(dish = result.data)
                }
                is Action.Error -> {
                    _dishState.value = DishDetailsState(
                        error = result.message ?: "Нәрсәдер начар булып чыккан"
                    )
                }
                is Action.Loading -> {
                    _dishState.value = DishDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        if (id != -1) getDishById(id)
    }

    fun processFavorites(id: Int) {
        viewModelScope.launch {
            updateFavDishUseCase(id, _isFavorite.value)
            _isFavorite.value = !_isFavorite.value
        }
    }

    fun load(id: Int) {
        this.id = id
        getDishById(id)
    }
}
