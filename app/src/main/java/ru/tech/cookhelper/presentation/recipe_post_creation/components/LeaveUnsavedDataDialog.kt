package ru.tech.cookhelper.presentation.recipe_post_creation.components

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
fun LeaveUnsavedDataDialog(title: Int, message: Int, onLeave: () -> Unit) {
    val dialogController = LocalDialogController.current

    AlertDialog(
        title = { Text(stringResource(title), textAlign = TextAlign.Center) },
        text = { Text(stringResource(message), textAlign = TextAlign.Center) },
        onDismissRequest = { dialogController.close() },
        icon = { Icon(dialogController.currentDialog.icon, null) },
        confirmButton = {
            TextButton(onClick = { dialogController.close() }) {
                Text(stringResource(R.string.cancel))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dialogController.close()
                onLeave()
            }) {
                Text(stringResource(R.string.leave_without_saving))
            }
        }
    )
}
