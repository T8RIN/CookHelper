package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.compositionLocalOf
import ru.tech.cookhelper.presentation.app.components.FancyToastValues

val LocalToastHost = compositionLocalOf { FancyToastValues() }