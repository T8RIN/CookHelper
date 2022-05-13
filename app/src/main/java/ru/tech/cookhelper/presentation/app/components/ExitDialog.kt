package ru.tech.cookhelper.presentation.app.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.close
import ru.tech.cookhelper.presentation.ui.utils.provider.currentDialog

@Composable
fun ExitDialog(activity: ComponentActivity) {
    val dialogController = LocalDialogController.current

    AlertDialog(
        title = { Text(stringResource(R.string.app_closing)) },
        text = {
            Text(
                stringResource(R.string.app_closing_message),
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = { dialogController.close() },
        icon = { Icon(dialogController.currentDialog.icon, null) },
        confirmButton = {
            TextButton(onClick = { dialogController.close() }) {
                Text(stringResource(R.string.stay))
            }
        },
        dismissButton = {
            TextButton(onClick = { activity.finishAffinity() }) {
                Text(stringResource(R.string.close))
            }
        }
    )
}