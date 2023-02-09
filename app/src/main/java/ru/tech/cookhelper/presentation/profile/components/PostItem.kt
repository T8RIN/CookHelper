package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.domain.model.Post
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.theme.LikeColor
import ru.tech.cookhelper.presentation.ui.utils.compose.squareSize
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Picture
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PostItem(
    post: Post,
    clientUserId: Long,
    onImageClick: (image: FileData) -> Unit,
    onAuthorClick: (author: User) -> Unit,
    onPostClick: () -> Unit,
    onLikeClick: () -> Unit,
    onCommentsClick: () -> Unit = onPostClick
) {
    val timestamp by remember {
        derivedStateOf {
            val df =
                if (Calendar.getInstance()[Calendar.YEAR] != SimpleDateFormat(
                        "yyyy",
                        Locale.getDefault()
                    )
                        .format(post.timestamp)
                        .toInt()
                ) {
                    SimpleDateFormat("d MMMM yyyy HH:mm", Locale.getDefault())
                } else SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())
            df.format(post.timestamp)
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onPostClick() }
    ) {
        Spacer(Modifier.size(16.dp))
        AuthorBubble(
            modifier = Modifier.padding(start = 20.dp),
            author = post.author,
            timestamp = timestamp,
            onClick = { onAuthorClick(post.author) }
        )
        Spacer(Modifier.size(16.dp))

        post.label.letNotEmpty { title ->
            Text(
                text = title,
                Modifier.padding(horizontal = 20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(Modifier.size(5.dp))
        }

        post.text.letNotEmpty { text ->
            Text(text = text, Modifier.padding(horizontal = 20.dp), fontSize = 16.sp)
            Spacer(Modifier.size(10.dp))
        }

        post.images.getOrNull(0)?.let { image ->
            val shape = RoundedCornerShape(4.dp)
            Box(Modifier.fillMaxWidth()) {
                Picture(
                    model = image.link,
                    modifier = Modifier
                        .squareSize()
                        .align(Alignment.Center)
                        .clickable {
                            onImageClick(image)
                        },
                    shape = shape
                )
            }
            Spacer(Modifier.size(4.dp))
        }

        Row(Modifier.padding(horizontal = 15.dp)) {
            val liked = post.likes.contains(clientUserId)
            PostActionButton(
                onClick = { onLikeClick() },
                icon = if (liked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                text = post.likes.size.run { if (this != 0) toString() else "" },
                contentColor = if (liked) LikeColor else Gray,
                containerColor = (if (liked) LikeColor else MaterialTheme.colorScheme.secondaryContainer).copy(
                    alpha = 0.25f
                )
            )
            Spacer(Modifier.size(8.dp))
            PostActionButton(
                onClick = { onCommentsClick() },
                icon = Icons.Rounded.ChatBubbleOutline,
                text = post.comments.size.run { if (this != 0) toString() else "" }
            )
        }
        Spacer(Modifier.size(15.dp))
    }
}

private inline fun String?.letNotEmpty(block: (String) -> Unit) {
    if (this?.isNotEmpty() == true) return block(this)
}
