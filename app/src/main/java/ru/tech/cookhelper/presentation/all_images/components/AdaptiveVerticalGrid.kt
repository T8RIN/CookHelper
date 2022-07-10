package ru.tech.cookhelper.presentation.all_images.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.domain.model.Image
import ru.tech.cookhelper.presentation.app.components.Picture

@Composable
fun AdaptiveVerticalGrid(images: List<Image>, onImageClick: (id: String) -> Unit) {
    val configuration = LocalConfiguration.current

    val portrait = configuration.screenWidthDp < configuration.screenHeightDp
    val count = if (portrait) 3 else 4

    LazyVerticalGrid(
        columns = GridCells.Fixed(count),
        contentPadding = PaddingValues(
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 80.dp,
            start = 2.dp, end = 2.dp, top = 4.dp
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        items(images) { item ->
            Picture(
                model = item.link,
                modifier = Modifier
                    .size(configuration.screenWidthDp.dp / count)
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .clickable { onImageClick(item.id) },
                shape = RoundedCornerShape(4.dp),
            )
        }
    }
}