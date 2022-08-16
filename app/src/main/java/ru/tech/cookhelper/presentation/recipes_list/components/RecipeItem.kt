package ru.tech.cookhelper.presentation.recipes_list.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator

@Composable
fun RecipeItem(recipe: Recipe, onClick: (id: Int) -> Unit) {
    Column(
        Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable {
                onClick(recipe.id)
            }) {
        Row(Modifier.padding(10.dp)) {

            Picture(
                model = recipe.iconUrl,
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f),
                shape = RoundedCornerShape(24.dp)
            )

            Spacer(Modifier.size(20.dp))
            Column(
                Modifier
                    .weight(1f)
                    .height(100.dp)
            ) {
                Spacer(Modifier.size(10.dp))
                Text(recipe.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.weight(1f))
                Text(
                    recipe.products.joinToString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 4
                )
                Spacer(Modifier.size(10.dp))
            }
            Spacer(Modifier.size(20.dp))
            Column(
                Modifier.height(100.dp)
            ) {
                Spacer(Modifier.size(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.AvTimer, null)
                    Spacer(Modifier.size(15.dp))
                    Text(
                        stringResource(R.string.cook_time_adapt_short, recipe.cookTime),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.size(5.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.LocalFireDepartment,
                        null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(Modifier.size(15.dp))
                    Text(
                        "${recipe.proteins}/${recipe.fats}/${recipe.carboh}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Spacer(Modifier.size(10.dp))
            }
        }
        Spacer(Modifier.size(10.dp))
        Separator()
    }
}