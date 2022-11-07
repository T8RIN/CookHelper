package ru.tech.cookhelper.presentation.matched_recipes.components

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.MatchedRecipe
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.ui.theme.Fridge
import ru.tech.cookhelper.presentation.ui.theme.Green
import ru.tech.cookhelper.presentation.ui.theme.Orange
import ru.tech.cookhelper.presentation.ui.theme.Red

@Composable
fun MatchedRecipeItem(matchedRecipe: MatchedRecipe, onClick: () -> Unit) {
    val recipe = matchedRecipe.recipe
    val percent: Float by remember {
        derivedStateOf {
            matchedRecipe
                .percentString
                .split("/")
                .let {
                    (it[0].toFloatOrNull() ?: 0f)
                        .div(it[1].toIntOrNull() ?: 1)
                }
        }
    }
    var pictureWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(
        Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .animateContentSize()
    ) {
        Row(Modifier.padding(10.dp)) {
            Picture(
                modifier = Modifier
                    .height(if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) pictureWidth else 100.dp)
                    .weight(1f)
                    .onSizeChanged { with(density) { pictureWidth = it.width.toDp() / 2 } }
                    .border(5.dp, percent.calcColor(), RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop,
                shape = RoundedCornerShape(24.dp),
                model = recipe.image.link
            )
            Spacer(Modifier.size(20.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(100.dp)
            ) {
                Spacer(Modifier.height(10.dp))
                Text(recipe.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(
                    Modifier
                        .weight(1f)
                        .padding(bottom = 4.dp))
                Text(
                    recipe.ingredients.joinToString(", ") { it.title },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
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
                    Text(
                        stringResource(R.string.cook_time_min, recipe.time),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Fridge,
                        null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(Modifier.width(15.dp))
                    Text(
                        text = matchedRecipe.percentString,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Spacer(Modifier.width(10.dp))
            }
        }
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun Float.calcColor(): Color = when {
    this >= 2 / 3f -> Green
    this >= 1 / 3f -> Orange
    else -> Red
}
