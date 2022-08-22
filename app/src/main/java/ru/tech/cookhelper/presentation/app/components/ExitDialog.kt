package ru.tech.cookhelper.presentation.app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DoorBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.DialogShape
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.close

@Composable
fun ExitDialog(onExit: () -> Unit) {
    val dialogController = LocalDialogController.current

    AlertDialog(
        title = { Text(stringResource(R.string.app_closing)) },
        text = {
            Text(
                stringResource(R.string.app_closing_message),
                textAlign = TextAlign.Center
            )
        },
        shape = DialogShape,
        onDismissRequest = { dialogController.close() },
        icon = { Icon(Icons.Outlined.DoorBack, null) },
        confirmButton = {
            TextButton(onClick = { dialogController.close() }) {
                Text(stringResource(R.string.stay))
            }
        },
        dismissButton = {
            TextButton(onClick = { onExit() }) {
                Text(stringResource(R.string.exit))
            }
        }
    )
}