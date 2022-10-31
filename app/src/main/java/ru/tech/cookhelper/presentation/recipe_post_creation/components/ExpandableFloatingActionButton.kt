package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableFloatingActionButton(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    size: FabSize = FabSize.Common,
    shape: Shape = size.shape,
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