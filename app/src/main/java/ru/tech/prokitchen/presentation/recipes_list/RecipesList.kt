package ru.tech.prokitchen.presentation.recipes_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Error
import androidx.compose.material.icons.twotone.FindInPage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.tech.prokitchen.R
import ru.tech.prokitchen.domain.model.Recipe
import ru.tech.prokitchen.presentation.app.components.Placeholder
import ru.tech.prokitchen.presentation.recipes_list.components.CuisineItem
import ru.tech.prokitchen.presentation.recipes_list.viewModel.CuisineViewModel
import ru.tech.prokitchen.presentation.ui.utils.rememberForeverLazyListState
import ru.tech.prokitchen.presentation.ui.utils.showSnackbar

@Composable
fun RecipesList(
    snackState: SnackbarHostState,
    searchString: MutableState<String>,
    onRecipeClick: (id: Int) -> Unit,
    viewModel: CuisineViewModel = hiltViewModel()
) {

    val state = viewModel.cuisineState.value

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.recipeList != null) {

            var data: List<Recipe> = state.recipeList

            if (searchString.value.isNotEmpty()) {
                data = state.recipeList.filter {
                    it.products.joinToString().lowercase().contains(searchString.value)
                        .or(it.title.lowercase().contains(searchString.value))
                }
                if (data.isEmpty()) {
                    Placeholder(
                        icon = Icons.TwoTone.FindInPage,
                        text = stringResource(R.string.nothing_found_by_the_search)
                    )
                }
            }
            LazyColumn(state = rememberForeverLazyListState(key = "recipes")) {
                items(data.size) { index ->
                    CuisineItem(data[index]) {
                        onRecipeClick(it)
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        } else if (!state.isLoading) {
            Placeholder(icon = Icons.TwoTone.Error, text = stringResource(R.string.smt_went_wrong))
        }

        if (state.error.isNotBlank()) {
            showSnackbar(
                rememberCoroutineScope(),
                snackState,
                state.error,
                "Тагын"
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
