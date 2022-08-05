package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import ru.tech.cookhelper.presentation.app.components.FancyToastValues

val LocalToastHost = compositionLocalOf { mutableStateOf(FancyToastValues()) }