package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(if (modifier == Modifier) Modifier.fillMaxSize() else modifier) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}