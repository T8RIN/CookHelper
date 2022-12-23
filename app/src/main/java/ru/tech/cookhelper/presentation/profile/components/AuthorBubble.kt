package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.model.getLastAvatar
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.ui.theme.Gray

@Composable
fun AuthorBubble(
    modifier: Modifier = Modifier,
    pictureModifier: Modifier = Modifier.size(54.dp),
    author: User?,
    timestamp: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Picture(
            model = author.getLastAvatar(),
            modifier = pictureModifier
        )
        Spacer(Modifier.size(8.dp))
        Column {
            Text(
                text = "${author?.name} ${author?.surname}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(Modifier.size(5.dp))
            Text(
                text = timestamp,
                color = Gray,
                fontSize = 14.sp
            )
        }
        Spacer(Modifier.size(8.dp))
    }
}