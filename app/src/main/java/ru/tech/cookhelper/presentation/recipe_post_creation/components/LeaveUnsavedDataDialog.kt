package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
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
fun LeaveUnsavedDataDialog(title: Int, message: Int, onLeave: () -> Unit) {
    val dialogController = LocalDialogController.current

    AlertDialog(
        shape = DialogShape,
        title = { Text(stringResource(title), textAlign = TextAlign.Center) },
        text = { Text(stringResource(message), textAlign = TextAlign.Center) },
        onDismissRequest = { dialogController.close() },
        icon = { Icon(Icons.Outlined.Save, null) },
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
