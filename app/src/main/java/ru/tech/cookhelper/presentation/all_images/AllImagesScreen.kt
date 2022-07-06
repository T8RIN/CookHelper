package ru.tech.cookhelper.presentation.all_images

import androidx.compose.runtime.Composable
import ru.tech.cookhelper.domain.model.Image
import ru.tech.cookhelper.presentation.all_images.components.AdaptiveVerticalGrid
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.currentScreen
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate

@Composable
fun AllImagesScreen(images: List<Image>, canAddImages: Boolean, goBack: () -> Unit) {
    val screenController = LocalScreenController.current

    AdaptiveVerticalGrid(
        images,
        onImageClick = {
            screenController.navigate(
                Screen.FullscreenImage(
                    id = it,
                    images = images,
                    previousScreen = screenController.currentScreen
                )
            )
        }
    )

}
