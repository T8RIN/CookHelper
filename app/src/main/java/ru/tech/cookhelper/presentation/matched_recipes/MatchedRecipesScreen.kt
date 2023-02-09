package ru.tech.cookhelper.presentation.matched_recipes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.matched_recipes.components.MatchedRecipeItem
import ru.tech.cookhelper.presentation.matched_recipes.viewModel.MatchedRecipesViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.utils.compose.TopAppBarUtils.topAppBarScrollBehavior
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Placeholder
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHostState
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate
import ru.tech.cookhelper.presentation.ui.widgets.Loading
import ru.tech.cookhelper.presentation.ui.widgets.TopAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MatchedRecipesScreen(
    onBack: () -> Unit,
    viewModel: MatchedRecipesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val toastHost = LocalToastHostState.current

    val screenController = LocalScreenController.current

    val scrollBehavior = topAppBarScrollBehavior()
    Column {
        TopAppBar(
            title = { Text(stringResource(R.string.matched_recipes)) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Rounded.ArrowBack, null)
                }
            },
            scrollBehavior = scrollBehavior
        )
        AnimatedContent(targetState = viewModel.matchedRecipesState.isLoading) { loading ->
            if (!loading) {
                AnimatedContent(targetState = viewModel.matchedRecipesState.recipes.isEmpty()) { empty ->
                    if (!empty) {
                        LazyColumn(
                            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                            contentPadding = WindowInsets.navigationBars.asPaddingValues()
                        ) {
                            itemsIndexed(viewModel.matchedRecipesState.recipes) { index, matchedRecipe ->
                                MatchedRecipeItem(
                                    matchedRecipe = matchedRecipe,
                                    onClick = {
                                        screenController.navigate(Screen.RecipeDetails(matchedRecipe.recipe))
                                    }
                                )
                                if (index != viewModel.matchedRecipesState.recipes.lastIndex) Separator()
                            }
                        }
                    } else {
                        Placeholder(
                            icon = Icons.Rounded.SearchOff,
                            text = stringResource(R.string.products_not_matched)
                        )
                    }
                }
            } else {
                Loading()
            }
        }
    }

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.show(
                it.icon,
                it.text.asString(context)
            )
            else -> {}
        }
    }

    BackHandler(onBack = onBack)
}