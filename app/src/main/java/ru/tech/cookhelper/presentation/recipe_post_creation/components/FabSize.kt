package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape

sealed class FabSize(
    val horizontalPadding: Dp,
    val spacerPadding: Dp,
    val iconSize: Dp,
    val shape: Shape
) {
    object Small : FabSize(
        iconSize = 24.dp,
        spacerPadding = 4.dp,
        horizontalPadding = 8.dp,
        shape = SquircleShape(12.dp)
    )

    object Common : FabSize(
        iconSize = 24.dp,
        spacerPadding = 12.dp,
        horizontalPadding = 16.dp,
        shape = SquircleShape(16.dp)
    )

    object Large : FabSize(
        iconSize = FloatingActionButtonDefaults.LargeIconSize,
        spacerPadding = 20.dp,
        horizontalPadding = 24.dp,
        shape = SquircleShape(28.dp)
    )
}