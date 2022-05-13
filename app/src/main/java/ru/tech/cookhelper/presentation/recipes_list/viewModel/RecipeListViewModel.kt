package ru.tech.cookhelper.presentation.recipes_list.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.use_case.get_recipes_list.GetRecipeListUseCase
import ru.tech.cookhelper.presentation.recipes_list.components.RecipeState
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ViewModel() {

    private val _recipeState = mutableStateOf(RecipeState())
    val recipeState: State<RecipeState> = _recipeState

    init {
        getCuisine()
    }

    private fun getCuisine() {
        getRecipeListUseCase().onEach { result ->
            when (result) {
                is Action.Success -> {
                    _recipeState.value = RecipeState(recipeList = result.data)
                }
                is Action.Error -> {
                    _recipeState.value = RecipeState(
                        error = result.message ?: "Нәрсәдер начар булып чыккан"
                    )
                }
                is Action.Loading -> {
                    _recipeState.value = RecipeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun reload() {
        getCuisine()
    }

}