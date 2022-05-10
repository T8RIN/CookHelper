package ru.tech.prokitchen.presentation.favourite_dishes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CloudOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.tech.prokitchen.R
import ru.tech.prokitchen.presentation.app.components.Placeholder
import ru.tech.prokitchen.presentation.favourite_dishes.viewModel.FavouriteListViewModel
import ru.tech.prokitchen.presentation.recipes_list.components.RecipeItem
import ru.tech.prokitchen.presentation.ui.utils.rememberForeverLazyListState
import ru.tech.prokitchen.presentation.ui.utils.showSnackbar

@Composable
fun FavouriteListScreen(
    snackState: SnackbarHostState,
    onRecipeClicked: (id: Int) -> Unit,
    viewModel: FavouriteListViewModel = hiltViewModel()
) {

    val state = viewModel.favState.value

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.recipeList != null) {
            LazyColumn(state = rememberForeverLazyListState(key = "fav")) {
                items(state.recipeList.size) { index ->
                    RecipeItem(state.recipeList[index]) {
                        onRecipeClicked(it)
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        } else if (!state.isLoading) {
            Placeholder(icon = Icons.TwoTone.CloudOff, text = stringResource(R.string.no_favs))
        }

        if (state.error.isNotBlank()) {
            showSnackbar(
                rememberCoroutineScope(),
                snackState,
                state.error,
                stringResource(R.string.again)
            ) {
                if (it == SnackbarResult.ActionPerformed) {
                    viewModel.reload()
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}