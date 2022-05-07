package ru.tech.prokitchen.presentation.ui.utils.provider

import androidx.compose.runtime.compositionLocalOf
import ru.tech.prokitchen.presentation.app.components.SettingsState

val LocalSettingsProvider = compositionLocalOf { SettingsState() }