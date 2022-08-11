package ru.tech.cookhelper.presentation.chat.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.model.User
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatBubbleItem(user: User?, message: Message) {
    val myMessage = (user?.id ?: 0) == message.userId

    val horizontalArrangement =
        if (myMessage) Arrangement.End
        else Arrangement.Start

    val color =
        if (myMessage) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant

    val shape = if (myMessage) RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 4.dp,
        bottomEnd = 4.dp,
        bottomStart = 16.dp
    )
    else RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 4.dp)

    val paddingValues = if (myMessage) PaddingValues(end = 12.dp, bottom = 4.dp) else PaddingValues(
        start = 12.dp,
        bottom = 4.dp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        horizontalArrangement = horizontalArrangement
    ) {
        Card(colors = CardDefaults.cardColors(containerColor = color), shape = shape) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(message.text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                Text(message.timestamp.toDate(), fontSize = 12.sp, modifier = Modifier.padding(4.dp), color = contentColorFor(color).copy(alpha = 0.5f))
            }
        }
    }
}

private fun Long.toDate(
    pattern: String = "HH:mm"
): String = SimpleDateFormat(pattern, Locale.getDefault()).format(this)