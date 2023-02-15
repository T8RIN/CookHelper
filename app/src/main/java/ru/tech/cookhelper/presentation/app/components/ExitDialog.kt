package ru.tech.cookhelper.presentation.app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DoorBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.DialogShape

@Composable
fun ExitDialog(onExit: () -> Unit, onDismissRequest: () -> Unit) {
    AlertDialog(
        title = { Text(stringResource(R.string.app_closing)) },
        text = {
            Text(
                stringResource(R.string.app_closing_message),
                textAlign = TextAlign.Center
            )
        },
        shape = DialogShape,
        onDismissRequest = onDismissRequest,
        icon = { Icon(Icons.Outlined.DoorBack, null) },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(stringResource(R.string.stay))
            }
        },
        dismissButton = {
            FilledTonalButton(onClick = onExit) {
                Text(stringResource(R.string.exit))
            }
        }
    )
}