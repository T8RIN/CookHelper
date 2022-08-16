package ru.tech.cookhelper.presentation.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.presentation.ui.utils.ColorUtils.createInverseSecondaryColor

@Composable
fun MessageHeader(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant.createInverseSecondaryColor(0.3f)
                .copy(alpha = 0.5f)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}