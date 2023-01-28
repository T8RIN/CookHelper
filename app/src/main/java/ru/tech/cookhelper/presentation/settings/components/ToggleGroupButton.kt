package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun ToggleGroupButton(
    modifier: Modifier = defaultModifier,
    items: List<Int>,
    selectedIndex: Int,
    indexChanged: (Int) -> Unit
) {
    val cornerRadius = 24.dp

    Row(modifier = modifier) {
        if (modifier == defaultModifier) Spacer(modifier = Modifier.weight(1f))

        items.forEachIndexed { index, item ->
            OutlinedButton(
                onClick = { indexChanged(index) },
                modifier = Modifier
                    .widthIn(min = 48.dp)
                    .then(
                        when (index) {
                            0 ->
                                Modifier
                                    .offset(0.dp, 0.dp)
                                    .zIndex(if (selectedIndex == 0) 1f else 0f)
                            else ->
                                Modifier
                                    .offset((-1 * index).dp, 0.dp)
                                    .zIndex(if (selectedIndex == index) 1f else 0f)
                        }
                    ),
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
                    else -> RoundedCornerShape(0.dp)
                },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                colors = if (selectedIndex == index) ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
                else ButtonDefaults.outlinedButtonColors()
            ) {
                Text(
                    text = stringResource(item),
                    color = if (selectedIndex == index) MaterialTheme.colorScheme.onTertiaryContainer
                    else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (modifier == defaultModifier) Spacer(modifier = Modifier.weight(1f))
    }
}

private var defaultModifier = Modifier
    .fillMaxWidth()
    .padding(8.dp)