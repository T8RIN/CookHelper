package ru.tech.cookhelper.presentation.ui.utils.compose.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavigationBarsSpacer() {
    Spacer(Modifier.padding(WindowInsets.navigationBars.asPaddingValues()))
}

@Composable
fun StatusBarsSpacer() {
    Spacer(Modifier.padding(WindowInsets.statusBars.asPaddingValues()))
}

@Composable
fun SystemBarsSpacer() {
    Spacer(Modifier.padding(WindowInsets.systemBars.asPaddingValues()))
}

