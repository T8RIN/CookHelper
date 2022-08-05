package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.domain.model.RecipePost
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.ui.theme.LikeColor

@Composable
fun ProfileRecipeItem(recipePost: RecipePost) {
    Card(
        Modifier
            .heightIn(min = 250.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Picture(
            model = recipePost.recipe.iconUrl,
            modifier = Modifier
                .weight(2f)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(24.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = recipePost.recipe.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                PostActionButton(
                    onClick = { },
                    icon = if (recipePost.liked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    text = recipePost.likeCount.toString(),
                    contentColor = if (recipePost.liked) LikeColor else Color.Gray,
                    containerColor = (if (recipePost.liked) LikeColor else MaterialTheme.colorScheme.secondaryContainer).copy(
                        alpha = 0.25f
                    )
                )
                Spacer(Modifier.size(8.dp))
                PostActionButton(
                    onClick = { },
                    icon = Icons.Rounded.ChatBubbleOutline,
                    text = recipePost.commentsCount.toString()
                )
            }
        }
    }
}