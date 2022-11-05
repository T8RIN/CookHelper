package ru.tech.cookhelper.presentation.fridge_screen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FindReplace
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.utils.kotlin.cptlize
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.fridge_screen.viewModel.FridgeViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.FabSize
import ru.tech.cookhelper.presentation.ui.theme.SausageOff
import ru.tech.cookhelper.presentation.ui.utils.compose.ScrollUtils.isScrollingUp
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun FridgeScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: FridgeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val toastHost = LocalToastHost.current

    val screenController = LocalScreenController.current
    val dialogController = LocalDialogController.current

    val lazyListState = rememberLazyListState()

    val fridge by remember {
        derivedStateOf {
            viewModel.userState.user?.fridge?.map {
                it.copy(title = it.title.cptlize())
            } ?: emptyList()
        }
    }

    Box(Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = fridge.isNotEmpty(),
            transitionSpec = { fadeIn() with fadeOut() }
        ) { notEmpty ->
            if (notEmpty) {
                LazyColumn(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    state = lazyListState,
                    contentPadding = PaddingValues(bottom = if (fridge.isEmpty()) 88.dp else 128.dp)
                ) {
                    items(fridge) {
                        Text(it.title)
                    }
                }
            } else {
                Placeholder(
                    icon = Icons.Filled.SausageOff,
                    text = stringResource(R.string.fridge_is_empty)
                )
            }
        }


        val pickProducts = {
            dialogController.show(
                Dialog.PickProducts(viewModel.allProducts - fridge.toSet()) {
                    viewModel.addProductsToFridge(it)
                }
            )
        }

        val empty = fridge.isEmpty()

        Column(
            horizontalAlignment = Alignment.End, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            AnimatedVisibility(
                visible = !empty,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ExpandableFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    expanded = false,
                    icon = {
                        Icon(Icons.Rounded.Add, null, modifier = Modifier.size(it))
                    },
                    size = FabSize.Small,
                    onClick = { pickProducts() }
                )
            }
            Spacer(Modifier.height(4.dp))
            ExpandableFloatingActionButton(
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
                    else pickProducts()
                }
            )
        }
    }

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.sendToast(
                it.icon,
                it.text.asString(context)
            )
            else -> {}
        }
    }
}