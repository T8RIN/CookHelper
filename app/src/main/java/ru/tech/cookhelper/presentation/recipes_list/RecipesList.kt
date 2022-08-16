package ru.tech.cookhelper.presentation.recipes_list

import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.presentation.recipes_list.viewModel.RecipeListViewModel

@Composable
fun RecipesList(
    searchBy: String,
    onRecipeClick: (id: Int) -> Unit,
    viewModel: RecipeListViewModel = hiltViewModel()
) {

}
