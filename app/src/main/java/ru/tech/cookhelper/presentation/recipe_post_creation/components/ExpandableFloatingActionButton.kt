package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
    text: @Composable () -> Unit = {},
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    val horizontalPadding by animateDpAsState(targetValue = if (expanded) 16.dp else 0.dp)
    val content: @Composable () -> Unit = {
        Row(
            modifier = Modifier.padding(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            AnimatedVisibility(visible = expanded) {
                Row {
                    Spacer(Modifier.width(12.dp))
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
                content = content
            )
        }
    }
}

enum class FabSize { Small, Common, Large }