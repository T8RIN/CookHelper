package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.domain.model.Post
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.ui.theme.LikeColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PostItem(
    post: Post,
    authorLoader: (authorId: String) -> User,
    onImageClick: (imageId: String) -> Unit,
    onAuthorClick: (authorId: String) -> Unit,
    onPostClick: (postId: String) -> Unit,
    onLikeClick: (postId: String) -> Unit,
    onCommentsClick: (postId: String) -> Unit = onPostClick
) {
    val author = authorLoader(post.authorId)

    val timestamp by derivedStateOf {
        val df =
            if (Calendar.getInstance()[Calendar.YEAR] != SimpleDateFormat("yyyy").format(
                    post.timestamp
                )
                    .toInt()
            ) {
                SimpleDateFormat("d MMMM yyyy HH:mm", Locale.getDefault())
            } else {
                SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())
            }
        df.format(post.timestamp)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onPostClick(post.postId) }) {
        Spacer(Modifier.size(15.dp))
        Row(
            Modifier
                .padding(start = 20.dp)
                .clip(CircleShape)
                .clickable { onAuthorClick(post.authorId) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Picture(
                model = author.avatar,
                modifier = Modifier.size(54.dp)
            )
            Spacer(Modifier.size(8.dp))
            Column {
                Text(
                    text = "${author.name} ${author.surname}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = timestamp,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            Spacer(Modifier.size(8.dp))
        }
        Spacer(Modifier.size(16.dp))

        post.title?.let { title ->
            Text(
                text = title,
                Modifier.padding(horizontal = 20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(Modifier.size(5.dp))
        }

        post.text?.let { text ->
            Text(text = text, Modifier.padding(horizontal = 20.dp), fontSize = 16.sp)
            Spacer(Modifier.size(10.dp))
        }

        post.image?.let { image ->
            Picture(
                model = image.link,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .heightIn(min = 300.dp)
                    .clickable {
                        onImageClick(image.id)
                    },
                shape = RectangleShape
            )
            Spacer(Modifier.size(4.dp))
        }

        Row(Modifier.padding(horizontal = 15.dp)) {
            PostActionButton(
                onClick = { onLikeClick(post.postId) },
                icon = if (post.liked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                text = post.likeCount.toString(),
                contentColor = if (post.liked) LikeColor else Color.Gray,
                containerColor = (if (post.liked) LikeColor else MaterialTheme.colorScheme.secondaryContainer).copy(
                    alpha = 0.25f
                )
            )
            Spacer(Modifier.size(8.dp))
            PostActionButton(
                onClick = { onCommentsClick(post.postId) },
                icon = Icons.Rounded.ChatBubbleOutline,
                text = post.commentsCount.toString()
            )
        }
        Spacer(Modifier.size(15.dp))
    }

}