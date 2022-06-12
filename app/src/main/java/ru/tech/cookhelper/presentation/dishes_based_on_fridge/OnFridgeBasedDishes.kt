package ru.tech.cookhelper.presentation.dishes_based_on_fridge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.twotone.Error
import androidx.compose.material.icons.twotone.FilterAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Loading
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.app.components.Size
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.dishes_based_on_fridge.viewModel.OnFridgeBasedDishesViewModel
import ru.tech.cookhelper.presentation.recipes_list.components.RecipeItem
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.name
import ru.tech.cookhelper.presentation.ui.utils.rememberForeverLazyListState
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@ExperimentalMaterial3Api
@Composable
fun OnFridgeBasedDishes(
    onRecipeClicked: (id: Int) -> Unit,
    goBack: () -> Unit,
    viewModel: OnFridgeBasedDishesViewModel = scopedViewModel(ignoreDisposing = listOf(Screen.RecipeDetails::class))
) {

    val state = viewModel.dishes.value

    val topAppBarScrollState = rememberTopAppBarScrollState()
    val scrollBehavior by remember {
        mutableStateOf(
            TopAppBarDefaults.pinnedScrollBehavior(
                topAppBarScrollState
            )
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    size = Size.Small,
                    title = { Text(stringResource(R.string.matched_recipes)) },
                    navigationIcon = {
                        IconButton(onClick = { goBack() }) {
                            Icon(Icons.Rounded.ArrowBack, null)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (state.recipeList?.isNotEmpty() == true) {
                    LazyColumn(
                        state = rememberForeverLazyListState(Screen.MatchedRecipes::class.name)
                    ) {
                        items(state.recipeList.size) { index ->
                            Row {
                                Text(
                                    "${state.recipeList[index].second}%",
                                    modifier = Modifier.padding(top = 10.dp, start = 10.dp)
                                )
                                RecipeItem(state.recipeList[index].first) {
                                    onRecipeClicked(it)
                                }
                            }
                        }
                    }
                } else if (!state.isLoading) {
                    Placeholder(
                        icon = Icons.TwoTone.FilterAlt,
                        text = stringResource(R.string.empty_matched_recipes)
                    )
                }

                if (state.error.isNotBlank()) {
                    Placeholder(icon = Icons.TwoTone.Error, text = state.error)
                }

                if (state.isLoading) {
                    Loading()
                }
            }
        }
    }

}