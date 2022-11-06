package ru.tech.cookhelper.presentation.feed_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WifiTetheringOff
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Loading
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.feed_screen.viewModel.FeedViewModel
import ru.tech.cookhelper.presentation.profile.components.ProfileRecipeItem
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel()) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(viewModel.feedState.data) {
        if (lazyListState.firstVisibleItemScrollOffset == 0) {
            lazyListState.animateScrollToItem(0)
        }
    }

    AnimatedContent(
        targetState = viewModel.feedState.isLoading,
        modifier = Modifier.fillMaxSize()
    ) { isLoading ->
        if (isLoading && viewModel.feedState.data.isEmpty()) {
            Loading()
        } else if (!isLoading && viewModel.feedState.data.isNotEmpty()) {
            LazyColumn(contentPadding = PaddingValues(top = 8.dp), state = lazyListState) {
                items(
                    items = viewModel.feedState.data,
                    key = { it.id }
                ) { item ->
                    ProfileRecipeItem(
                        currentUser = viewModel.user.user,
                        recipePost = item,
                        onRecipeClick = {
                            //TODO: Open Recipe fullscreen like a post
                        },
                        onAuthorClick = {
                            //TODO: Open Author page
                        }
                    )
                }
            }
        } else if (!isLoading && viewModel.feedState.data.isEmpty()) {
            Placeholder(
                icon = Icons.Outlined.WifiTetheringOff,
                text = stringResource(R.string.feed_error)
            )
        }
    }

    val toastHost = LocalToastHost.current
    val context = LocalContext.current
    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.sendToast(
                Icons.Rounded.ErrorOutline,
                it.text.asString(context)
            )
            else -> {}
        }
    }
}