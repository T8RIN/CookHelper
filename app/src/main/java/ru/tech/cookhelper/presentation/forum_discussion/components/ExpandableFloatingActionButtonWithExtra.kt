package ru.tech.cookhelper.presentation.forum_discussion.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.recipe_post_creation.components.FabSize

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableFloatingActionButtonWithExtra(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    isExtra: Boolean = false,
    size: FabSize = FabSize.Common,
    shape: Shape = size.shape,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    text: @Composable () -> Unit = {},
    icon: @Composable (iconSize: Dp) -> Unit,
    expandedContent: @Composable () -> Unit,
    transitionSpec: AnimatedContentScope<Boolean>.() -> ContentTransform = {
        fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)) with
                fadeOut(animationSpec = tween(90))
    },
    onClick: () -> Unit
) {
    val localRipple = LocalRippleTheme.current
    val horizontalPadding by animateDpAsState(targetValue = if (expanded) size.horizontalPadding else 0.dp)
    val content: @Composable () -> Unit = {
        CompositionLocalProvider(LocalRippleTheme provides localRipple) {
            AnimatedContent(targetState = isExtra, transitionSpec = transitionSpec) {
                if (it) {
                    expandedContent()
                } else {
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
            }
        }
    }

    val _onClick = { if (!isExtra) onClick() }

    CompositionLocalProvider(LocalRippleTheme provides if (!isExtra) LocalRippleTheme.current else NoRippleTheme) {
        when (size) {
            FabSize.Small -> {
                SmallFloatingActionButton(
                    modifier = modifier,
                    onClick = _onClick,
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
                    onClick = _onClick,
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
                    onClick = _onClick,
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
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}