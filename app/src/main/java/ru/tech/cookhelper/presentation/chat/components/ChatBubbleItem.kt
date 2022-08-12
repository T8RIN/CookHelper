package ru.tech.cookhelper.presentation.chat.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.domain.model.Message
import ru.tech.cookhelper.domain.model.User
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatBubbleItem(
    user: User?,
    message: Message,
    cutTopCorner: Boolean,
    showPointingArrow: Boolean
) {
    val myMessage = (user?.id ?: 0) == message.userId

    val horizontalArrangement =
        if (myMessage) Arrangement.End
        else Arrangement.Start

    val bubbleColor =
        if (myMessage) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant

    val bubbleShape = if (myMessage) RoundedCornerShape(
        topStart = 16.dp,
        topEnd = if (cutTopCorner) 16.dp else 4.dp,
        bottomEnd = if (showPointingArrow) 0.dp else 4.dp,
        bottomStart = 16.dp
    )
    else RoundedCornerShape(
        topStart = if (cutTopCorner) 16.dp else 4.dp,
        topEnd = 16.dp,
        bottomEnd = 16.dp,
        bottomStart = if (showPointingArrow) 0.dp else 4.dp
    )

    val bubblePadding = if (myMessage) PaddingValues(
        end = if (showPointingArrow) 0.dp else 14.dp,
        bottom = 4.dp
    ) else PaddingValues(
        start = if (showPointingArrow) 0.dp else 14.dp,
        bottom = 4.dp
    )

    val canvasPadding = if (myMessage) PaddingValues(
        end = 2.dp,
        bottom = 4.dp
    ) else PaddingValues(
        start = 2.dp,
        bottom = 4.dp
    )

    val timePadding = if (myMessage) PaddingValues(4.dp)
    else PaddingValues(top = 4.dp, start = 4.dp, bottom = 4.dp, end = 8.dp)

    var placeTimeUnderTheText by remember { mutableStateOf(false) }

    var maxWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val canvasModifier = Modifier
        .height(24.dp)
        .width(14.dp)
        .padding(canvasPadding)

    val bottomArrow = @Composable {
        if (showPointingArrow) {
            Box {
                Canvas(canvasModifier) {
                    val path = Path().apply {
                        if (!myMessage) {
                            moveTo(size.width, 0f)
                            lineTo(size.width, size.height)
                            lineTo(0f, size.height)
                        } else {
                            moveTo(0f, 0f)
                            lineTo(0f, size.height)
                            lineTo(size.width, size.height)
                        }
                    }
                    drawPath(
                        path = path,
                        brush = SolidColor(bubbleColor)
                    )
                }
                Box(
                    modifier = canvasModifier
                        .padding(bottom = 1.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(
                                topStartPercent = 0,
                                topEndPercent = 0,
                                bottomStartPercent = if (myMessage) 100 else 0,
                                bottomEndPercent = if (myMessage) 0 else 100
                            )
                        )
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                maxWidth = with(density) { it.size.width.toDp() * 0.85f }
            },
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!myMessage) bottomArrow()
        Card(
            colors = CardDefaults.cardColors(containerColor = bubbleColor),
            shape = bubbleShape,
            modifier = Modifier
                .padding(bubblePadding)
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
                        color = contentColorFor(bubbleColor).copy(alpha = 0.5f),
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
                        color = contentColorFor(bubbleColor).copy(alpha = 0.5f),
                        maxLines = 1,
                    )
                }
            }
        }
        if (myMessage) bottomArrow()
    }
}

private fun Long.toDate(
    pattern: String = "HH:mm"
): String = SimpleDateFormat(pattern, Locale.getDefault()).format(this)