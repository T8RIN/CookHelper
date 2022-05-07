package ru.tech.prokitchen.presentation.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun ToggleGroup(items: List<Int>, selectedIndex: Int, indexChanged: (Int) -> Unit) {
    val cornerRadius = 8.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        items.forEachIndexed { index, item ->
            OutlinedButton(
                onClick = { indexChanged(index) },
                modifier = when (index) {
                    0 ->
                        Modifier
                            .offset(0.dp, 0.dp)
                            .zIndex(if (selectedIndex == index) 1f else 0f)
                    else ->
                        Modifier
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (selectedIndex == index) 1f else 0f)
                },
                shape = when (index) {
                    0 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = 0.dp,
                        bottomStart = cornerRadius,
                        bottomEnd = 0.dp
                    )
                    items.size - 1 -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = cornerRadius,
                        bottomStart = 0.dp,
                        bottomEnd = cornerRadius
                    )
                    else -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                },
                border = BorderStroke(
                    1.dp, if (selectedIndex == index) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.DarkGray.copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedIndex == index) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.1f
                        ), contentColor = MaterialTheme.colorScheme.primary
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                },
            ) {
                Text(
                    text = stringResource(item),
                    color = if (selectedIndex == index) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.DarkGray.copy(alpha = 0.9f)
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}