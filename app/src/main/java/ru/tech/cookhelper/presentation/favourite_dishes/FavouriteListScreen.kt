package ru.tech.cookhelper.presentation.favourite_dishes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CloudOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.favourite_dishes.viewModel.FavouriteListViewModel
import ru.tech.cookhelper.presentation.recipes_list.components.RecipeItem
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.clicked
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalSnackbarHost
import ru.tech.cookhelper.presentation.ui.utils.rememberForeverLazyListState
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel
import ru.tech.cookhelper.presentation.ui.utils.showSnackbar

@Composable
fun FavouriteListScreen(
    onRecipeClicked: (id: Int) -> Unit,
    viewModel: FavouriteListViewModel = scopedViewModel(ignoreDisposing = listOf(Screen.RecipeDetails::class))
) {

    val state = viewModel.favState.value

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.recipeList != null) {
            LazyColumn(state = rememberForeverLazyListState(key = "fav")) {
                items(state.recipeList) { item ->
                    RecipeItem(recipe = item, onClick = { onRecipeClicked(it) })
                }
            }
        } else if (!state.isLoading) {
            Placeholder(
                icon = Icons.TwoTone.CloudOff,
                text = stringResource(R.string.no_favourite_dishes)
            )
        }

        if (state.error.isNotBlank()) {
            showSnackbar(
                rememberCoroutineScope(),
                LocalSnackbarHost.current,
                state.error,
                stringResource(R.string.again)
            ) {
                if (it.clicked) viewModel.reload()
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}