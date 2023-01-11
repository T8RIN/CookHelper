package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun RotationButton(
    modifier: Modifier = Modifier,
    rotated: Boolean = false,
    onClick: () -> Unit,
) {
    val rotation: Float by animateFloatAsState(if (rotated) 180f else 0f)
    IconButton(
        onClick = onClick,
        modifier = modifier.rotate(rotation),
    ) {
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.size(26.dp)
        )
    }
}