package ru.tech.cookhelper.presentation.chat.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatBubbleItem(
    isMessageFromCurrentUser: Boolean,
    text: String,
    timestamp: Long,
    cutTopCorner: Boolean,
    showPointingArrow: Boolean,
    cornerRadius: Dp = 16.dp,
    bubbleColor: Color = if (isMessageFromCurrentUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
) {
    val horizontalArrangement =
        if (isMessageFromCurrentUser) Arrangement.End
        else Arrangement.Start

    val bubbleShape = if (isMessageFromCurrentUser) RoundedCornerShape(
        topStart = cornerRadius,
        topEnd = if (cutTopCorner) cornerRadius else 4.dp,
        bottomEnd = if (showPointingArrow) 0.dp else 4.dp,
        bottomStart = cornerRadius
    )
    else RoundedCornerShape(
        topStart = if (cutTopCorner) cornerRadius else 4.dp,
        topEnd = cornerRadius,
        bottomEnd = cornerRadius,
        bottomStart = if (showPointingArrow) 0.dp else 4.dp
    )

    val minPadding = min(LocalConfiguration.current.screenWidthDp.dp * 0.15f, 48.dp)

    val bubblePadding = if (isMessageFromCurrentUser) PaddingValues(
        end = if (showPointingArrow) 0.dp else 14.dp,
        start = minPadding,
        bottom = 4.dp
    ) else PaddingValues(
        start = if (showPointingArrow) 0.dp else 14.dp,
        end = minPadding,
        bottom = 4.dp
    )

    val canvasPadding = if (isMessageFromCurrentUser) PaddingValues(
        end = 2.dp,
        bottom = 4.dp
    ) else PaddingValues(
        start = 2.dp,
        bottom = 4.dp
    )

    var placeTimeUnderTheText by remember { mutableStateOf(false) }

    val timePadding = if (isMessageFromCurrentUser) PaddingValues(
        top = 4.dp, start = if(placeTimeUnderTheText) 8.dp else 4.dp, bottom = 4.dp, end = 4.dp
    )
    else PaddingValues(top = 4.dp, start = 4.dp, bottom = 4.dp, end = 8.dp)

    val canvasModifier = Modifier
        .height(14.dp)
        .width(10.dp)
        .padding(canvasPadding)
        .scale(scaleX = if (!isMessageFromCurrentUser) -1f else 1f, scaleY = 1f)

    val bottomArrow: @Composable RowScope.() -> Unit = {
        if (showPointingArrow) {
            if (!isMessageFromCurrentUser) Spacer(Modifier.width(4.dp))
            Canvas(canvasModifier) {
                val path = Path().apply {
                    val y = size.height
                    val x = 0f
                    moveTo(x, y)
                    cubicTo(
                        x1 = size.width * 2f,
                        y1 = y,
                        x2 = x,
                        y2 = y,
                        x3 = x,
                        y3 = x
                    )
                }
                drawPath(
                    path = path,
                    brush = SolidColor(bubbleColor)
                )
            }
            if (isMessageFromCurrentUser) Spacer(Modifier.width(4.dp))
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isMessageFromCurrentUser) bottomArrow()
        Card(
            colors = CardDefaults.cardColors(containerColor = bubbleColor),
            shape = bubbleShape,
            modifier = Modifier
                .weight(1f, false)
                .padding(bubblePadding)
        ) {
            if (!placeTimeUnderTheText) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                            .weight(1f, false),
                        onTextLayout = { textLayoutResult ->
                            if (textLayoutResult.lineCount > 1) placeTimeUnderTheText = true
                        }
                    )
                    Text(
                        text = timestamp.toDate(),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(timePadding),
                        color = contentColorFor(bubbleColor).copy(alpha = 0.5f),
                        maxLines = 1,
                    )
                }
            } else {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 4.dp),
                        onTextLayout = { textLayoutResult ->
                            if (textLayoutResult.lineCount > 1) placeTimeUnderTheText = true
                        }
                    )
                    Text(
                        text = timestamp.toDate(),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(timePadding),
                        color = contentColorFor(bubbleColor).copy(alpha = 0.5f),
                        maxLines = 1,
                    )
                }
            }
        }
        if (isMessageFromCurrentUser) bottomArrow()
    }
}

private fun Long.toDate(
    pattern: String = "HH:mm"
): String = SimpleDateFormat(pattern, Locale.getDefault()).format(this)