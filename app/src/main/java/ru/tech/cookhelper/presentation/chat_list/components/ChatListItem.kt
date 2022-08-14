package ru.tech.cookhelper.presentation.chat_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.presentation.app.components.Picture
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatListItem(
    image: String?,
    title: String,
    lastMessageText: String,
    lastMessageTimestamp: Long,
    newMessagesCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (image != null) {
            Picture(
                model = image,
                modifier = Modifier
                    .padding(8.dp)
                    .size(56.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (title.getOrNull(0)?.toString() ?: "*").uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .height(72.dp)
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = lastMessageText,
                fontSize = 16.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .height(72.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = lastMessageTimestamp.toDate(),
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
            if (newMessagesCount != 0) {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 24.dp, minHeight = 24.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = newMessagesCount.toString(),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

private fun Long.toDate(
    pattern: String = "HH:mm"
): String = SimpleDateFormat(pattern, Locale.getDefault()).format(this)