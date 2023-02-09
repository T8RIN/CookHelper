package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.theme.LikeColor
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.utils.android.ShareUtils.shareWith
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Picture
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileRecipeItem(
    currentUser: User?,
    recipePost: Recipe,
    onAuthorClick: (authorId: Long) -> Unit,
    onRecipeClick: (id: Long) -> Unit
) {
    val context = LocalContext.current

    val timestamp by remember {
        derivedStateOf {
            val df =
                if (Calendar.getInstance()[Calendar.YEAR] != SimpleDateFormat(
                        "yyyy",
                        Locale.getDefault()
                    )
                        .format(recipePost.timestamp)
                        .toInt()
                ) {
                    SimpleDateFormat("d MMMM yyyy HH:mm", Locale.getDefault())
                } else SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())
            df.format(recipePost.timestamp)
        }
    }

    Card(
        modifier = Modifier
            .heightIn(min = 300.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        shape = SquircleShape(24.dp),
        onClick = { onRecipeClick(recipePost.id) }
    ) {
        AuthorBubble(
            modifier = Modifier.padding(top = 12.dp, start = 12.dp),
            author = recipePost.author,
            timestamp = timestamp,
            onClick = { onAuthorClick(recipePost.author.id) }
        )
        Spacer(Modifier.size(16.dp))
        Picture(
            model = recipePost.image.link,
            modifier = Modifier
                .height(175.dp)
                .padding(bottom = 8.dp),
            shape = SquircleShape(24.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = recipePost.title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier
                        .padding(top = 4.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            CircleShape
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(Icons.Rounded.AvTimer, null)
                        Text(
                            text = recipePost.time.toString(),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = recipePost.ingredients.joinToString(", ") { it.title },
                fontSize = 16.sp
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                val liked = remember(recipePost) { recipePost.likes.contains(currentUser?.id) }
                PostActionButton(
                    onClick = { /*TODO: Like feature */ },
                    icon = if (liked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    text = recipePost.likes.size.toString(),
                    contentColor = if (liked) LikeColor else Gray,
                    containerColor = if (liked) LikeColor.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.1f
                    )
                )
                Spacer(Modifier.size(8.dp))
                PostActionButton(
                    onClick = { /*TODO: Open comments feature */ },
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
                    icon = Icons.Rounded.ChatBubbleOutline,
                    text = recipePost.comments.size.toString()
                )
                Spacer(Modifier.weight(1f))
                PostActionButton(
                    onClick = { context.shareWith(recipePost.toShareValue()) },
                    icon = Icons.Rounded.Share,
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
                    text = ""
                )
            }
            Spacer(Modifier.height(6.dp))
        }
    }

}