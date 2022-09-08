package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    topAppBarSize: TopAppBarSize = TopAppBarSize.Small,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    background: Color? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val colors =
        if (background != null) TopAppBarDefaults.smallTopAppBarColors(containerColor = background)
        else TopAppBarDefaults.smallTopAppBarColors()

    when (topAppBarSize) {
        TopAppBarSize.Small -> {
            TopAppBar(
                title = title,
                modifier = modifier,
                navigationIcon = navigationIcon,
                actions = actions,
                scrollBehavior = scrollBehavior,
                colors = colors,
                windowInsets = windowInsets
            )
        }
        TopAppBarSize.Centered -> {
            CenterAlignedTopAppBar(
                title = title,
                modifier = modifier,
                navigationIcon = navigationIcon,
                actions = actions,
                scrollBehavior = scrollBehavior,
                colors = colors,
                windowInsets = windowInsets
            )
        }
        TopAppBarSize.Medium -> {
            MediumTopAppBar(
                title = title,
                modifier = modifier,
                navigationIcon = navigationIcon,
                actions = actions,
                scrollBehavior = scrollBehavior,
                colors = colors,
                windowInsets = windowInsets
            )
        }
        TopAppBarSize.Large -> {
            LargeTopAppBar(
                title = title,
                modifier = modifier,
                navigationIcon = navigationIcon,
                actions = actions,
                scrollBehavior = scrollBehavior,
                colors = colors,
                windowInsets = windowInsets
            )
        }
    }
}

enum class TopAppBarSize { Small, Centered, Medium, Large }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedTopAppBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    topAppBarSize: TopAppBarSize = TopAppBarSize.Small,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    background: Color? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    visible: Boolean = true,
    enter: EnterTransition = fadeIn() + expandVertically(),
    exit: ExitTransition = fadeOut() + shrinkVertically()
) {
    AnimatedVisibility(
        visible = visible,
        enter = enter,
        exit = exit
    ) {
        TopAppBar(
            topAppBarSize = topAppBarSize,
            scrollBehavior = scrollBehavior,
            modifier = modifier,
            windowInsets = windowInsets,
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            background = background
        )
    }
}