package ru.tech.cookhelper.presentation.forum_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Hexagon
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.ForumFilters
import ru.tech.cookhelper.presentation.app.components.containerColorWithScroll
import ru.tech.cookhelper.presentation.forum_screen.components.TabRow
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.FabSize
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.theme.CreateAlt
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.stringResourceListOf
import ru.tech.cookhelper.presentation.ui.utils.compose.ScrollUtils.isScrollingUp
import ru.tech.cookhelper.presentation.ui.utils.navigation.BottomSheet
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ForumScreen(scrollBehavior: TopAppBarScrollBehavior) {
    val screenController = LocalScreenController.current
    val bottomSheetController = LocalBottomSheetController.current
    val tabColor by scrollBehavior.containerColorWithScroll()
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var filters by remember { mutableStateOf(ForumFilters.empty()) }

    LocalTopAppBarActions.current.setActions {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Rounded.Search, null)
        }
        IconButton(onClick = {
            scope.launch {
                bottomSheetController.show(
                    BottomSheet.ForumFilter(
                        filters = filters,
                        onFiltersChange = {
                            filters = it
                        }
                    )
                )
            }
        }) {
            Icon(Icons.Rounded.FilterList, null)
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column {
            var selectedTabIndex by remember { mutableStateOf(0) }
            TabRow(
                containerColor = tabColor,
                selectedTabIndex = selectedTabIndex,
                tabs = stringResourceListOf(R.string.all_topics, R.string.my_topics),
                divider = { Separator() },
                onTabClick = { selectedTabIndex = it }
            )
            AnimatedContent(targetState = selectedTabIndex == 0) { first ->
                if (first) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        ExpandableFloatingActionButton(
                            icon = {
                                Icon(
                                    Icons.Rounded.Hexagon,
                                    null,
                                    modifier = Modifier.size(it)
                                )
                            },
                            size = FabSize.Large,
                            onClick = {
                                screenController.navigate(
                                    Screen.ForumDiscussion(
                                        id = 0,
                                        "How to cook an dinner?"
                                    )
                                )
                            }
                        )
                    }
                } else {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(50) {
                            Separator()
                            Spacer(Modifier.size(50.dp))
                        }
                    }
                }
            }
        }

        ExpandableFloatingActionButton(
            expanded = lazyListState.isScrollingUp(),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            text = { Text(stringResource(R.string.create_topic)) },
            icon = { Icon(Icons.Rounded.CreateAlt, null) },
            onClick = { screenController.navigate(Screen.TopicCreation) }
        )

    }

}