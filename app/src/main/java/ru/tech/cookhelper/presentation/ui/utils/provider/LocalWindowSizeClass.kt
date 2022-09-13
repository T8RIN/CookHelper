package ru.tech.cookhelper.presentation.ui.utils.provider

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalWindowSizeClass = compositionLocalOf<WindowSizeClass> { error("SizeClass not present") }

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun Activity.provideWindowSizeClass(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalWindowSizeClass provides calculateWindowSizeClass(this),
        content = content
    )
}

