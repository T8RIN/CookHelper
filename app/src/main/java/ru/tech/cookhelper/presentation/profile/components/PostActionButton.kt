package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.25f),
    contentColor: Color = Color.Gray
) {
    Surface(
        modifier = Modifier.defaultMinSize(32.dp, 32.dp),
        shape = CircleShape,
        color = containerColor,
        onClick = onClick,
        contentColor = contentColor
    ) {
        ProvideTextStyle(value = TextStyle(fontWeight = FontWeight.Bold)) {
            Row(
                Modifier.padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(icon, null)
                Spacer(Modifier.size(8.dp))
                Text(text)
            }
        }
    }
}