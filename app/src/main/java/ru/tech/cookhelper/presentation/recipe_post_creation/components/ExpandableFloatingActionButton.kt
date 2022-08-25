package ru.tech.cookhelper.presentation.recipe_post_creation.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape

@Composable
fun ExpandableFloatingActionButton(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    size: FabSize = FabSize.Common,
    shape: Shape = when (size) {
        FabSize.Small -> SquircleShape(12.dp)
        FabSize.Common -> SquircleShape(16.dp)
        FabSize.Large -> SquircleShape(28.dp)
    },
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    text: @Composable () -> Unit = {},
    icon: @Composable (iconSize: Dp) -> Unit,
    onClick: () -> Unit
) {
    val horizontalPadding by animateDpAsState(targetValue = if (expanded) size.horizontalPadding else 0.dp)
    val content: @Composable () -> Unit = {
        Row(
            modifier = Modifier.padding(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon(size.iconSize)
            AnimatedVisibility(visible = expanded) {
                Row {
                    Spacer(Modifier.width(size.spacerPadding))
                    text()
                }
            }
        }
    }
    when (size) {
        FabSize.Small -> {
            SmallFloatingActionButton(
                modifier = modifier,
                onClick = onClick,
                shape = shape,
                containerColor = containerColor,
                contentColor = contentColor,
                interactionSource = interactionSource,
                elevation = elevation,
                content = content
            )
        }
        FabSize.Common -> {
            FloatingActionButton(
                modifier = modifier,
                onClick = onClick,
                shape = shape,
                containerColor = containerColor,
                contentColor = contentColor,
                interactionSource = interactionSource,
                elevation = elevation,
                content = content
            )
        }
        FabSize.Large -> {
            LargeFloatingActionButton(
                modifier = modifier,
                onClick = onClick,
                shape = shape,
                containerColor = containerColor,
                contentColor = contentColor,
                interactionSource = interactionSource,
                elevation = elevation,
                content = content
            )
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun observeExpansion(scrollState: ScrollState, onChange: (expanded: Boolean) -> Unit) {
    LaunchedEffect(scrollState) {
        var previousOffset = 0
        snapshotFlow {
            scrollState.value
        }.collect {
            onChange(it <= previousOffset)
            previousOffset = it
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun observeExpansion(
    lazyListState: LazyListState,
    byOffset: Boolean = false,
    onChange: (expanded: Boolean) -> Unit
) {
    LaunchedEffect(lazyListState) {
        var previous = 0
        snapshotFlow {
            if (!byOffset) lazyListState.firstVisibleItemIndex else lazyListState.firstVisibleItemScrollOffset
        }.collect { current ->
            onChange(previous <= current)
            previous = current
        }
    }
}

private val FabSize.horizontalPadding: Dp
    get() = when (this) {
        FabSize.Small -> 8.dp
        FabSize.Common -> 16.dp
        FabSize.Large -> 24.dp
    }

private val FabSize.spacerPadding: Dp
    get() = when (this) {
        FabSize.Small -> 4.dp
        FabSize.Common -> 12.dp
        FabSize.Large -> 20.dp
    }

private val FabSize.iconSize: Dp
    get() = when (this) {
        FabSize.Small, FabSize.Common -> 24.dp
        FabSize.Large -> FloatingActionButtonDefaults.LargeIconSize
    }

enum class FabSize { Small, Common, Large }