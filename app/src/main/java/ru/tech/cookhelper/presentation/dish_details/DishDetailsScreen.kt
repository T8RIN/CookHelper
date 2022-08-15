package ru.tech.cookhelper.presentation.dish_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.twotone.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.google.accompanist.flowlayout.FlowRow
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.app.components.Size
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.dish_details.components.InfoItem
import ru.tech.cookhelper.presentation.dish_details.viewModel.DishDetailsViewModel
import ru.tech.cookhelper.presentation.ui.utils.ShareUtils.shareWith

@ExperimentalMaterial3Api
@Composable
fun DishDetailsScreen(
    id: Int,
    goBack: () -> Unit,
    viewModel: DishDetailsViewModel = hiltViewModel(
        defaultArguments = bundleOf("id" to id)
    )
) {

    val state = viewModel.dishState.value

    val context = LocalContext.current

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior by remember {
        mutableStateOf(
            TopAppBarDefaults.enterAlwaysScrollBehavior(
                topAppBarState
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
                    size = Size.Large,
                    title = { Text(state.dish?.title ?: "") },
                    navigationIcon = {
                        IconButton(onClick = { goBack() }) {
                            Icon(Icons.Rounded.ArrowBack, null)
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    actions = {
                        IconButton(onClick = {
                            context.shareWith(viewModel.dishState.value.dish?.toShareValue())
                        }) {
                            Icon(Icons.Outlined.Share, null)
                        }
                    }
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (state.dish != null) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = WindowInsets.statusBars.asPaddingValues()
                                .calculateTopPadding(),
                            bottom = WindowInsets.navigationBars.asPaddingValues()
                                .calculateBottomPadding() + 100.dp
                        )
                    ) {
                        item {
                            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                                Picture(
                                    modifier = Modifier
                                        .height(200.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp),
                                    shape = RoundedCornerShape(24.dp),
                                    model = state.dish.iconUrl,
                                )

                                Spacer(modifier = Modifier.height(15.dp))
                                Text(
                                    text = stringResource(R.string.about_dish),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(modifier = Modifier.height(30.dp))

                                FlowRow(
                                    mainAxisSpacing = 10.dp,
                                    crossAxisSpacing = 10.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 15.dp)
                                ) {
                                    InfoItem(state.dish.category)
                                    InfoItem(
                                        stringResource(
                                            R.string.cook_time_adapt,
                                            state.dish.cookTime
                                        )
                                    )
                                    InfoItem(
                                        stringResource(
                                            R.string.prot_fat_carb,
                                            state.dish.proteins.s,
                                            state.dish.fats.s,
                                            state.dish.carboh.s
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    state.dish.products.joinToString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 15.dp)
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Text(
                                    text = stringResource(R.string.cook_steps),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                            }
                        }
                        items(state.dish.cookSteps.size) {
                            Text(
                                state.dish.cookSteps[it],
                                modifier = Modifier.padding(horizontal = 15.dp)
                            )
                            Spacer(Modifier.size(10.dp))
                        }
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .navigationBarsPadding()
                            .padding(end = 16.dp, bottom = 16.dp),
                        onClick = { viewModel.processFavorites(id) }
                    ) {
                        Icon(
                            if (viewModel.isFavorite.value) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            null
                        )
                    }
                } else if (!state.isLoading && state.error.isBlank()) {
                    Placeholder(
                        icon = Icons.TwoTone.Error,
                        text = stringResource(R.string.something_went_wrong)
                    )
                }

                if (state.error.isNotBlank()) {
                    Placeholder(icon = Icons.TwoTone.Error, text = state.error)
                }

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

}

private val Double.s: String
    get() {
        return this.toString()
    }