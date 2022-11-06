package ru.tech.cookhelper.presentation.matched_recipes

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.presentation.matched_recipes.viewModel.MatchedRecipesViewModel

@Composable
fun MatchedRecipesScreen(
    onBack: () -> Unit,
    viewModel: MatchedRecipesViewModel = hiltViewModel()
) {



    BackHandler(onBack = onBack)
}