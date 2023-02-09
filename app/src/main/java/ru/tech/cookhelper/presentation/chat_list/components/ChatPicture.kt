package ru.tech.cookhelper.presentation.chat_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Picture

@Composable
fun ChatPicture(
    modifier: Modifier,
    image: String?,
    title: String
) {
    if (image != null) {
        Picture(
            model = image,
            modifier = modifier
        )
    } else {
        Box(
            modifier = Modifier
                .then(modifier)
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (title.getOrNull(0)?.toString() ?: "#"),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}