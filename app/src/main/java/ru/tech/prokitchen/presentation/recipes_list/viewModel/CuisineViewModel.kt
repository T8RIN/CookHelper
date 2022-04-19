package ru.tech.prokitchen.presentation.recipes_list.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.prokitchen.core.Action
import ru.tech.prokitchen.domain.use_case.get_recipes_list.GetRecipeListUseCase
import ru.tech.prokitchen.presentation.recipes_list.components.CuisineState
import javax.inject.Inject

@HiltViewModel
class CuisineViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ViewModel() {

    private val _cuisineState = mutableStateOf(CuisineState())
    val cuisineState: State<CuisineState> = _cuisineState

    init {
        getCuisine()
    }

    private fun getCuisine() {
        getRecipeListUseCase().onEach { result ->
            when (result) {
                is Action.Success -> {
                    _cuisineState.value = CuisineState(recipeList = result.data)
                }
                is Action.Empty -> {
                    _cuisineState.value = CuisineState(
                        error = result.message ?: "Нәрсәдер начар булып чыккан"
                    )
                }
                is Action.Loading -> {
                    _cuisineState.value = CuisineState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun reload() {
        getCuisine()
    }

}