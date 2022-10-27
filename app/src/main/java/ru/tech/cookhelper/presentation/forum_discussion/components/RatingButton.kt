package ru.tech.cookhelper.presentation.forum_discussion.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.theme.Green
import ru.tech.cookhelper.presentation.ui.theme.Red

@Composable
fun RatingButton(
    modifier: Modifier = Modifier,
    userRate: Int,
    currentRating: String,
    onRateUp: () -> Unit,
    onRateDown: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f),
        contentColor = Gray
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledIconButton(
                onClick = onRateUp,
                modifier = Modifier.padding(vertical = 3.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = if (userRate == 1) Green.copy(alpha = 0.15f) else Color.Transparent
                )
            ) {
                Icon(
                    Icons.Rounded.ArrowUpward,
                    null,
                    tint = if (userRate == 1) Green else Gray
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(currentRating)
            Spacer(Modifier.width(4.dp))
            FilledIconButton(
                onClick = onRateDown,
                modifier = Modifier.padding(vertical = 3.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = if (userRate == -1) Red.copy(alpha = 0.15f) else Color.Transparent
                )
            ) {
                Icon(
                    Icons.Rounded.ArrowDownward,
                    null,
                    tint = if (userRate == -1) Red else Gray
                )
            }
        }
    }
}
