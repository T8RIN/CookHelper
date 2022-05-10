package ru.tech.prokitchen.presentation.favourite_dishes.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.prokitchen.core.Action
import ru.tech.prokitchen.domain.use_case.get_favourites.GetFavouriteDishesUseCase
import ru.tech.prokitchen.presentation.recipes_list.components.RecipeState
import javax.inject.Inject

@HiltViewModel
class FavouriteListViewModel @Inject constructor(
    private val getFavouriteDishesUseCase: GetFavouriteDishesUseCase
) : ViewModel() {

    private val _favState = mutableStateOf(RecipeState())
    val favState: State<RecipeState> = _favState

    init {
        getFavourites()
    }

    private fun getFavourites() {
        getFavouriteDishesUseCase().onEach { result ->
            when (result) {
                is Action.Success -> {
                    _favState.value = RecipeState(recipeList = result.data)
                }
                is Action.Error -> {
                    _favState.value = RecipeState(
                        error = result.message ?: "Нәрсәдер начар булып чыккан"
                    )
                }
                is Action.Loading -> {
                    _favState.value = RecipeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun reload() {
        getFavourites()
    }

}