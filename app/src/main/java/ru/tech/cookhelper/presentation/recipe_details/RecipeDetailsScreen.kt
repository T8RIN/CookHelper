package ru.tech.cookhelper.presentation.recipe_details

import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.presentation.recipe_details.viewModel.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(
    recipe: Recipe?,
    percentString: String,
    onBack: () -> Boolean,
    viewModel: RecipeDetailsViewModel = hiltViewModel()
) {

}