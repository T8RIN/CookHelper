package ru.tech.cookhelper.presentation.fridge_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.domain.model.Product

@Composable
fun ProductItem(
    modifier: Modifier,
    product: Product,
    onDelete: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = product.getIcon(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Spacer(
            Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Text(text = product.title)
        Spacer(
            Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Rounded.DeleteOutline, null)
        }
    }
}