package ru.tech.cookhelper.presentation.ui.utils.zooomable

import androidx.compose.ui.geometry.Offset

data class ZoomParams(
    val zoomEnabled: Boolean = false,
    val hideBarsOnTap: Boolean = false,
    val minZoomScale: Float = 1f,
    val maxZoomScale: Float = 4f,
    val onTap: (Offset) -> Unit = {}
)