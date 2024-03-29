package ru.tech.cookhelper.presentation.fridge_screen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FindReplace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.utils.kotlin.cptlize
import ru.tech.cookhelper.presentation.fridge_screen.components.ProductItem
import ru.tech.cookhelper.presentation.fridge_screen.viewModel.FridgeViewModel
import ru.tech.cookhelper.presentation.pick_products.PickProductsDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.FabSize
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.theme.SausageOff
import ru.tech.cookhelper.presentation.ui.utils.compose.ScrollUtils.isScrollingUp
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHostState
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate
import ru.tech.cookhelper.presentation.ui.widgets.Placeholder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun FridgeScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: FridgeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val toastHost = LocalToastHostState.current

    val screenController = LocalScreenController.current
    var showDialog by rememberSaveable { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()

    val fridge by remember {
        derivedStateOf {
            (viewModel.userState.user?.fridge?.map {
                it.copy(title = it.title.cptlize())
            } ?: emptyList()).asReversed()
        }
    }

    Box(Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = fridge.isNotEmpty()
        ) { notEmpty ->
            if (notEmpty) {
                LazyColumn(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    state = lazyListState,
                    contentPadding = PaddingValues(bottom = if (fridge.isEmpty()) 88.dp else 128.dp)
                ) {
                    itemsIndexed(fridge) { index, product ->
                        ProductItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            product = product,
                            onDelete = { viewModel.removeProductsFromFridge(listOf(product)) }
                        )
                        if (index != fridge.lastIndex) Separator()
                    }
                }
            } else {
                Placeholder(
                    icon = Icons.Filled.SausageOff,
                    text = stringResource(R.string.fridge_is_empty)
                )
            }
        }


        val empty = fridge.isEmpty()

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp)
        ) {
            AnimatedVisibility(
                visible = !empty,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ExpandableFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    expanded = false,
                    icon = {
                        Icon(
                            Icons.Rounded.Add,
                            null,
                            modifier = Modifier
                                .size(it)
                        )
                    },
                    size = FabSize.Small,
                    onClick = { showDialog = true }
                )
            }
            Spacer(Modifier.height(4.dp))
            ExpandableFloatingActionButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                expanded = lazyListState.isScrollingUp(),
                icon = {
                    Icon(
                        if (!empty) Icons.Rounded.FindReplace else Icons.Rounded.Add,
                        null,
                        modifier = Modifier.size(it)
                    )
                },
                text = {
                    if (!empty) Text(stringResource(R.string.match))
                    else Text(stringResource(R.string.add_products))
                },
                onClick = {
                    if (!empty) screenController.navigate(Screen.MatchedRecipes)
                    else showDialog = true
                }
            )
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

    if (showDialog) PickProductsDialog(
        onPicked = viewModel::addProductsToFridge,
        onDismiss = { showDialog = false }
    )
}