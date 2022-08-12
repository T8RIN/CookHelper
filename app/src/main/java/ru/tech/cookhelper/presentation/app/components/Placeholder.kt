package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun Placeholder(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Column(
        modifier = if (modifier == Modifier) Modifier.fillMaxSize() else modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, null, modifier = Modifier.fillMaxSize(0.3f))
        Text(text, textAlign = TextAlign.Center)
    }
}

fun Modifier.shimmer(visible: Boolean) = composed {
    then(
        Modifier.placeholder(
            visible = visible,
            color = MaterialTheme.colorScheme.surfaceVariant,
            highlight = PlaceholderHighlight.shimmer()
        )
    )
}