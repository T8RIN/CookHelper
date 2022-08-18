package ru.tech.cookhelper.presentation.dishes_based_on_fridge

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.presentation.dishes_based_on_fridge.viewModel.OnFridgeBasedDishesViewModel

@Composable
fun OnFridgeBasedDishes(
    onRecipeClicked: (id: Int) -> Unit,
    onBack: () -> Unit,
    viewModel: OnFridgeBasedDishesViewModel = hiltViewModel()
) {

    BackHandler { onBack() }
}