package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive

@Composable
fun Marquee(
    modifier: Modifier = Modifier,
    gradientEdgeColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable (Modifier) -> Unit
) {
    var xOffset by remember { mutableStateOf(0) }
    val layoutInfoState = remember { mutableStateOf<LayoutInfo?>(null) }

    LaunchedEffect(layoutInfoState.value) {
        val layoutInfo = layoutInfoState.value ?: return@LaunchedEffect
        if (layoutInfo.width <= layoutInfo.containerWidth) return@LaunchedEffect

        val duration = 7500 * layoutInfo.width / layoutInfo.containerWidth

        val animation = TargetBasedAnimation(
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = duration, easing = LinearEasing
                ), repeatMode = RepeatMode.Restart
            ),
            typeConverter = Int.VectorConverter,
            initialValue = 0,
            targetValue = -layoutInfo.width
        )

        val startTime = withFrameNanos { it }

        do {
            val playTime = withFrameNanos { it } - startTime
            xOffset = animation.getValueFromNanos(playTime)
        } while (isActive)
    }

    SubcomposeLayout(
        modifier = modifier.clipToBounds()
    ) { constraints ->
        val infiniteWidthConstraints = constraints.copy(maxWidth = Int.MAX_VALUE)

        var main = subcompose(MarqueeLayers.Main) {
            content(Modifier)
        }.first().measure(infiniteWidthConstraints)

        var gradient: Placeable? = null
        var secondPlaceableWithOffset: Pair<Placeable, Int>? = null

        if (main.width <= constraints.maxWidth) {
            main = subcompose(MarqueeLayers.Secondary) {
                content(Modifier.fillMaxWidth())
            }.first().measure(constraints)
            layoutInfoState.value = null
        } else {
            val spacing = constraints.maxWidth * 2 / 3
            layoutInfoState.value = LayoutInfo(
                width = main.width + spacing, containerWidth = constraints.maxWidth
            )
            val secondTextOffset = main.width + xOffset + spacing
            val secondTextSpace = constraints.maxWidth - secondTextOffset

            if (secondTextSpace > 0) {
                secondPlaceableWithOffset = subcompose(MarqueeLayers.Secondary) {
                    content(Modifier)
                }.first().measure(infiniteWidthConstraints) to secondTextOffset
            }
            gradient = subcompose(MarqueeLayers.EdgesGradient) {
                Gradient(gradientEdgeColor)
            }.first().measure(constraints = constraints.copy(maxHeight = main.height))
        }

        layout(
            width = constraints.maxWidth, height = main.height
        ) {
            main.place(xOffset, 0)
            secondPlaceableWithOffset?.let {
                it.first.place(it.second, 0)
            }
            gradient?.place(0, 0)
        }
    }
}

@Composable
private fun Gradient(gradientEdgeColor: Color) {
    Row {
        GradientEdge(startColor = gradientEdgeColor, endColor = Color.Transparent)
        Spacer(modifier = Modifier.weight(1f))
        GradientEdge(startColor = Color.Transparent, endColor = gradientEdgeColor)
    }
}

@Composable
private fun GradientEdge(
    startColor: Color, endColor: Color
) {
    Box(
        modifier = Modifier
            .width(10.dp)
            .fillMaxHeight()
            .background(
                brush = Brush.horizontalGradient(
                    0f to startColor, 1f to endColor
                )
            )
    )
}

private enum class MarqueeLayers {
    Main,
    Secondary,
    EdgesGradient
}

private data class LayoutInfo(
    val width: Int,
    val containerWidth: Int
)