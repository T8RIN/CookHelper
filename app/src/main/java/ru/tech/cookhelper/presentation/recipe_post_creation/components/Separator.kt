package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun Separator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
    thickness: Dp = DividerDefaults.Thickness
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}