package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.createSecondaryColor

@Composable
fun Separator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant.createSecondaryColor(0.05f),
    thickness: Dp = DividerDefaults.Thickness
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}