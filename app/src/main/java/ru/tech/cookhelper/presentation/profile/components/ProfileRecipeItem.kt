package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.domain.model.RecipePost
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.ui.theme.LikeColor
import ru.tech.cookhelper.presentation.ui.utils.ShareUtils.shareWith

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileRecipeItem(
    recipePost: RecipePost,
    onRecipeClick: (id: String) -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .heightIn(min = 250.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(24.dp),
        onClick = { onRecipeClick(recipePost.postId) }
    ) {
        Picture(
            model = recipePost.recipe.iconUrl,
            modifier = Modifier
                .height(150.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(24.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = recipePost.recipe.title,
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
                            text = recipePost.recipe.cookTime.toString(),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = recipePost.recipe.products.joinToString(", "),
                fontSize = 16.sp
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                PostActionButton(
                    onClick = { context.shareWith(recipePost.recipe.toShareValue()) },
                    icon = if (recipePost.liked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    text = recipePost.likeCount.toString(),
                    contentColor = if (recipePost.liked) LikeColor else Color.Gray,
                    containerColor = if (recipePost.liked) LikeColor.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.1f
                    )
                )
                Spacer(Modifier.size(8.dp))
                PostActionButton(
                    onClick = { },
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
                    icon = Icons.Rounded.ChatBubbleOutline,
                    text = recipePost.commentsCount.toString()
                )
                Spacer(Modifier.weight(1f))
                PostActionButton(
                    onClick = { /*TODO*/ },
                    icon = Icons.Rounded.Share,
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
                    text = ""
                )
            }
        }
    }
}