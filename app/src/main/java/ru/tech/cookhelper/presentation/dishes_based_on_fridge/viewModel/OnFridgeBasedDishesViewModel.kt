package ru.tech.cookhelper.presentation.dishes_based_on_fridge.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tech.cookhelper.domain.use_case.get_fridge_list.GetFridgeListUseCase
import ru.tech.cookhelper.domain.use_case.get_recipes_list.GetRecipeListUseCase
import ru.tech.cookhelper.presentation.dishes_based_on_fridge.components.PodborState
import javax.inject.Inject

@HiltViewModel
class OnFridgeBasedDishesViewModel @Inject constructor(
    private val getFridgeListUseCase: GetFridgeListUseCase,
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ViewModel() {

    private val _dishes = mutableStateOf(PodborState())
    val dishes: State<PodborState> = _dishes

}
