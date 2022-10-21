package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.tech.cookhelper.presentation.ui.theme.DialogShape

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(if (modifier == Modifier) Modifier.fillMaxSize() else modifier) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface, DialogShape),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(Modifier.padding(24.dp))
            }
        }
    }
}