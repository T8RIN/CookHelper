package ru.tech.cookhelper.presentation.ui.widgets

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalTopAppBarVisuals
import ru.tech.cookhelper.presentation.ui.utils.provider.TopAppBarActions
import ru.tech.cookhelper.presentation.ui.utils.provider.TopAppBarNavigationIcon

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
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
    val surface = MaterialTheme.colorScheme.surface
    val colors =
        if (background != null) TopAppBarDefaults.topAppBarColors(containerColor = background)
        else TopAppBarDefaults.topAppBarColors(
            containerColor = scrollBehavior?.containerColorWithScroll()?.takeIf {
                it != surface
            } ?: scrollBehavior?.containerColorWithCollapse() ?: surface
        )

    AnimatedContent(
        targetState = topAppBarSize,
        transitionSpec = { fadeIn() with fadeOut() }
    ) { size ->
        Box(modifier = Modifier.animateContentSize()) {
            when (size) {
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavIcon(
    targetState: TopAppBarNavigationIcon? = LocalTopAppBarVisuals.current.navigationIcon,
    defaultIcon: ImageVector = Icons.Rounded.Menu,
    onClick: () -> Unit,
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = { fadeIn() + scaleIn() with fadeOut() + scaleOut() }
    ) {
        it?.invoke() ?: IconButton(
            onClick = onClick,
            content = { Icon(defaultIcon, null) }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppBarActions(
    actions: TopAppBarActions? = LocalTopAppBarVisuals.current.actions
) {
    AnimatedVisibility(
        visible = actions != null,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut()
    ) {
        AnimatedContent(
            targetState = actions,
            transitionSpec = {
                fadeIn() + scaleIn() with fadeOut() + scaleOut()
            }
        ) {
            Row { it?.invoke(this) }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <S> AppBarTitle(
    targetState: S,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = { fadeIn() with fadeOut() + scaleOut() },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarScrollBehavior.containerColorWithScroll(
    containerColor: Color = MaterialTheme.colorScheme.surface,
    scrolledContainerColor: Color = MaterialTheme.colorScheme
        .surfaceColorAtElevation(3.dp)
) = animateColorAsState(
    targetValue = lerp(
        containerColor,
        scrolledContainerColor,
        FastOutLinearInEasing.transform(if (state.overlappedFraction > 0.01f) 1f else 0f)
    ),
    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
).value

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarScrollBehavior.containerColorWithCollapse(
    containerColor: Color = MaterialTheme.colorScheme.surface,
    collapsedContainerColor: Color = MaterialTheme.colorScheme
        .surfaceColorAtElevation(3.dp)
) = animateColorAsState(
    targetValue = lerp(
        containerColor,
        collapsedContainerColor,
        FastOutLinearInEasing.transform(state.collapsedFraction)
    ),
    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
).value