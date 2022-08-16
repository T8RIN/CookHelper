package ru.tech.cookhelper.presentation.favourite_dishes

import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.presentation.favourite_dishes.viewModel.FavouriteListViewModel

@Composable
fun FavouriteListScreen(
    onRecipeClicked: (id: Int) -> Unit,
    viewModel: FavouriteListViewModel = hiltViewModel()
) {



}