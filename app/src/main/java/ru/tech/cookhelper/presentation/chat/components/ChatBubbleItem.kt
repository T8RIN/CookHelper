package ru.tech.cookhelper.presentation.chat.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.model.User
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatBubbleItem(user: User?, message: Message, cutTopCorner: Boolean) {
    val myMessage = (user?.id ?: 0) == message.userId

    val horizontalArrangement =
        if (myMessage) Arrangement.End
        else Arrangement.Start

    val color =
        if (myMessage) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant

    val shape = if (myMessage) RoundedCornerShape(
        topStart = 16.dp,
        topEnd = if (cutTopCorner) 16.dp else 4.dp,
        bottomEnd = 4.dp,
        bottomStart = 16.dp
    )
    else RoundedCornerShape(
        topStart = if (cutTopCorner) 16.dp else 4.dp,
        topEnd = 16.dp,
        bottomEnd = 16.dp,
        bottomStart = 4.dp
    )

    val bubblePadding = if (myMessage) PaddingValues(end = 12.dp, bottom = 4.dp) else PaddingValues(
        start = 12.dp,
        bottom = 4.dp
    )

    val timePadding = if (myMessage) PaddingValues(4.dp)
    else PaddingValues(top = 4.dp, start = 4.dp, bottom = 4.dp, end = 8.dp)

    var placeTimeUnderTheText by remember { mutableStateOf(false) }

    var maxWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                maxWidth = with(density) { it.size.width.toDp() * 0.85f }
            }
            .padding(bubblePadding),
        horizontalArrangement = horizontalArrangement
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = color),
            shape = shape,
            modifier = Modifier
                .widthIn(max = maxWidth)
                .onGloballyPositioned {
                    val currentWidth = with(density) { it.size.width.toDp() }
                    if (currentWidth == maxWidth) {
                        placeTimeUnderTheText = true
                    }
                }
        ) {
            if (!placeTimeUnderTheText) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = message.text,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                            .weight(1f, false)
                    )
                    Text(
                        text = message.timestamp.toDate(),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(timePadding),
                        color = contentColorFor(color).copy(alpha = 0.5f),
                        maxLines = 1,
                    )
                }
            } else {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = message.text,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 4.dp)
                    )
                    Text(
                        text = message.timestamp.toDate(),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(timePadding),
                        color = contentColorFor(color).copy(alpha = 0.5f),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

private fun Long.toDate(
    pattern: String = "HH:mm"
): String = SimpleDateFormat(pattern, Locale.getDefault()).format(this)