package ru.tech.cookhelper.presentation.matched_recipes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator

@Composable
fun MatchedRecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Column(
        Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
    ) {
        Row(Modifier.padding(10.dp)) {
            Picture(
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                model = recipe.image.link
            )
            Spacer(Modifier.size(20.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
            ) {
                Spacer(Modifier.height(10.dp))
                Text(recipe.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.weight(1f))
                Text(
                    recipe.ingredients.joinToString(", ") { it.title },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 4
                )
                Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.width(20.dp))
            Column(Modifier.height(100.dp)) {
                Spacer(Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.AvTimer, null)
                    Spacer(Modifier.width(15.dp))
                    Text("${recipe.time} мин", textAlign = TextAlign.Center)
                }
                Spacer(Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.LocalFireDepartment,
                        null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(Modifier.width(15.dp))
                    Text(
                        "Б ${recipe.proteins}\nЖ ${recipe.fats}\nУ ${recipe.carbohydrates}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Spacer(Modifier.width(10.dp))
            }
        }
        Spacer(Modifier.height(10.dp))
    }
}