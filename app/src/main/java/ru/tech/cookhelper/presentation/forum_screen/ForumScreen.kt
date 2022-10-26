package ru.tech.cookhelper.presentation.forum_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.containerColorWithScroll
import ru.tech.cookhelper.presentation.forum_screen.components.TabRow
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.stringResourceListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(scrollBehavior: TopAppBarScrollBehavior) {
    val tabColor by scrollBehavior.containerColorWithScroll()
    Column {
        var selectedTabIndex by remember { mutableStateOf(0) }
        TabRow(
            containerColor = tabColor,
            selectedTabIndex = selectedTabIndex,
            tabs = stringResourceListOf(R.string.all_topics, R.string.my_topics),
            divider = { Separator() },
            onTabClick = { selectedTabIndex = it }
        )
        LazyColumn(Modifier.fillMaxSize()) {
            items(50) {
                Separator()
                Spacer(Modifier.size(50.dp))
            }
        }
    }
}
