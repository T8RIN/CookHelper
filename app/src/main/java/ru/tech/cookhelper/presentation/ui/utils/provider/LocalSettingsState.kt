package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.compositionLocalOf
import ru.tech.cookhelper.presentation.settings.components.SettingsState

val LocalSettingsProvider = compositionLocalOf<SettingsState> { error("SettingsState not present") }