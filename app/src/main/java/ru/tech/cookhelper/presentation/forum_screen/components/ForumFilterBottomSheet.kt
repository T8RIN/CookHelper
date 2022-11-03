package ru.tech.cookhelper.presentation.forum_screen.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.ui.theme.CreateAlt

@Composable
fun ForumFilterBottomSheet() {
    Placeholder(
        icon = Icons.Rounded.CreateAlt,
        text = "",
        Modifier
            .fillMaxWidth()
            .fillMaxSize(0.5f)
    )
}