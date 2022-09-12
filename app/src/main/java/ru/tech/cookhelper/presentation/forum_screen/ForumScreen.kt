package ru.tech.cookhelper.presentation.forum_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.profile.components.FlexibleTabRow
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ForumScreen() {
    LazyColumn(Modifier.fillMaxSize()) {
        stickyHeader {
            var selectedTabIndex by remember { mutableStateOf(0) }
            FlexibleTabRow(
                selectedTabIndex = selectedTabIndex,
                tabs = listOf(stringResource(R.string.posts), stringResource(R.string.recipes)),
                divider = { Separator() },
                onTabClick = { selectedTabIndex = it }
            )
        }
    }
}
