package ru.tech.prokitchen.presentation.recipes_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import ru.tech.prokitchen.R
import ru.tech.prokitchen.domain.model.Recipe
import ru.tech.prokitchen.presentation.app.components.shimmer

@Composable
fun RecipeItem(recipe: Recipe, onClick: (id: Int) -> Unit) {
    Column(
        Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable {
                onClick(recipe.id)
            }) {
        Row(Modifier.padding(10.dp)) {

            var shimmerVisible by rememberSaveable { mutableStateOf(true) }
            AsyncImage(
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.iconUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .weight(1f)
                    .shimmer(shimmerVisible),
                onSuccess = { shimmerVisible = false }
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
        Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.surfaceVariant)
    }
}