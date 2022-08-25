package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    topAppBarSize: TopAppBarSize = TopAppBarSize.Small,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    background: Color? = null,
    enableTopPadding: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val backgroundColors = TopAppBarDefaults.smallTopAppBarColors()

    val backgroundColor = background ?: backgroundColors.containerColor(
        colorTransitionFraction = scrollBehavior?.state?.overlappedFraction ?: 0f
    ).value

    val paddingModifier = if (enableTopPadding) modifier.statusBarsPadding() else Modifier

    val foregroundColors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )

    Surface(color = backgroundColor) {
        when (topAppBarSize) {
            TopAppBarSize.Small -> {
                SmallTopAppBar(
                    title,
                    paddingModifier,
                    navigationIcon,
                    actions,
                    foregroundColors,
                    scrollBehavior
                )
            }
            TopAppBarSize.Centered -> {
                CenterAlignedTopAppBar(
                    title,
                    paddingModifier,
                    navigationIcon,
                    actions,
                    foregroundColors,
                    scrollBehavior
                )
            }
            TopAppBarSize.Medium -> {
                MediumTopAppBar(
                    title,
                    paddingModifier,
                    navigationIcon,
                    actions,
                    foregroundColors,
                    scrollBehavior
                )
            }
            TopAppBarSize.Large -> {
                LargeTopAppBar(
                    title,
                    paddingModifier,
                    navigationIcon,
                    actions,
                    foregroundColors,
                    scrollBehavior
                )
            }
        }
    }
}

enum class TopAppBarSize { Small, Centered, Medium, Large }