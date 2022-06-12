package ru.tech.cookhelper.presentation.dishes_based_on_fridge.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.domain.use_case.get_fridge_list.GetFridgeListUseCase
import ru.tech.cookhelper.domain.use_case.get_recipes_list.GetRecipeListUseCase
import ru.tech.cookhelper.presentation.dishes_based_on_fridge.components.PodborState
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class OnFridgeBasedDishesViewModel @Inject constructor(
    private val getFridgeListUseCase: GetFridgeListUseCase,
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ViewModel() {

    private val _dishes = mutableStateOf(PodborState())
    val dishes: State<PodborState> = _dishes

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            getFridgeListUseCase().collect { result ->
                when (result) {
                    is Action.Success, is Action.Empty -> {
                        val fridgeList: List<Int> = (result.data ?: emptyList()).map { it.id }

                        getRecipeListUseCase().collect { result1 ->
                            when (result1) {
                                is Action.Success -> {
                                    val availableList = result1.data ?: emptyList()
                                    val matchedList = fridgeList calcMatchedList availableList

                                    _dishes.value = PodborState(recipeList = matchedList)
                                }
                                is Action.Error -> {
                                    _dishes.value = PodborState(error = result1.message.toString())
                                }
                                is Action.Loading -> {
                                    _dishes.value = PodborState(isLoading = true)
                                }
                                else -> {
                                    _dishes.value = PodborState(recipeList = emptyList())
                                }
                            }
                        }
                    }
                    is Action.Loading -> {
                        _dishes.value = PodborState(isLoading = true)
                    }
                    is Action.Error -> {
                        _dishes.value = PodborState(error = result.message ?: "")
                    }
                }
            }
        }
    }

}

@Suppress("RedundantSuspendModifier")
private suspend infix fun List<Int>.calcMatchedList(availableList: List<Recipe>): List<Pair<Recipe, Int>> {
    val matchedList = arrayListOf<Pair<Recipe, Int>>()

    availableList.forEach { dish ->
        var cnt = 0

        this.forEach { id -> if (dish.productIds.contains(id)) cnt++ }

        val coeff =
            (cnt / dish.productIds.size.toFloat() * 100).roundToInt()
        if (coeff > 29) {
            matchedList.add(Pair(dish, coeff))
        }
    }

    return matchedList.sortedByDescending { it.second }
}
