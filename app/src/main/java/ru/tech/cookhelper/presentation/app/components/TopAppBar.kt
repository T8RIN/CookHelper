package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    size: Size = Size.Small,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    background: Color? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val backgroundColors = TopAppBarDefaults.smallTopAppBarColors()

    val backgroundColor = background ?: backgroundColors.containerColor(
        scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    ).value

    val foregroundColors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )

    Surface(color = backgroundColor) {
        when (size) {
            Size.Small -> {
                SmallTopAppBar(
                    title,
                    modifier.statusBarsPadding(),
                    navigationIcon,
                    actions,
                    foregroundColors,
                    scrollBehavior
                )
            }
            Size.Centered -> {
                CenterAlignedTopAppBar(
                    title,
                    modifier.statusBarsPadding(),
                    navigationIcon,
                    actions,
                    foregroundColors,
                    scrollBehavior
                )
            }
            Size.Medium -> {
                MediumTopAppBar(
                    title,
                    modifier.statusBarsPadding(),
                    navigationIcon,
                    actions,
                    foregroundColors,
                    scrollBehavior
                )
            }
            Size.Large -> {
                LargeTopAppBar(
                    title,
                    modifier.statusBarsPadding(),
                    navigationIcon,
                    actions,
                    foregroundColors,
                    scrollBehavior
                )
            }
        }
    }
}

enum class Size { Small, Centered, Medium, Large }