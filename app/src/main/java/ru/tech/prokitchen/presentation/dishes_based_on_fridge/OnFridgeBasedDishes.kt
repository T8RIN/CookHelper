package ru.tech.prokitchen.presentation.dishes_based_on_fridge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.twotone.Error
import androidx.compose.material.icons.twotone.FilterAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.tech.prokitchen.R
import ru.tech.prokitchen.presentation.app.components.Loading
import ru.tech.prokitchen.presentation.app.components.Placeholder
import ru.tech.prokitchen.presentation.dishes_based_on_fridge.viewModel.OnFridgeBasedDishesViewModel
import ru.tech.prokitchen.presentation.recipes_list.components.RecipeItem

@ExperimentalMaterial3Api
@Composable
fun OnFridgeBasedDishes(
    onRecipeClicked: (id: Int) -> Unit,
    goBack: () -> Unit,
    viewModel: OnFridgeBasedDishesViewModel = hiltViewModel()
) {

    val state = viewModel.dishes.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                val backgroundColors = TopAppBarDefaults.smallTopAppBarColors()
                val backgroundColor = backgroundColors.containerColor(
                    scrollFraction = viewModel.scrollBehavior.scrollFraction
                ).value
                val foregroundColors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                )

                Surface(color = backgroundColor) {
                    SmallTopAppBar(
                        modifier = Modifier.statusBarsPadding(),
                        title = { Text(stringResource(R.string.matched_recipes)) },
                        navigationIcon = {
                            IconButton(onClick = { goBack() }) {
                                Icon(Icons.Rounded.ArrowBack, null)
                            }
                        },
                        scrollBehavior = viewModel.scrollBehavior,
                        colors = foregroundColors
                    )
                }
            },
            modifier = Modifier.nestedScroll(viewModel.scrollBehavior.nestedScrollConnection)
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (state.recipeList?.isNotEmpty() == true) {
                    LazyColumn {
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
                        text = stringResource(R.string.empty_podbor)
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