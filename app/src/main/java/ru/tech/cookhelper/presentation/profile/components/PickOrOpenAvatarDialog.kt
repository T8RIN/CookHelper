package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.DialogShape
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.close

@Composable
fun PickOrOpenAvatarDialog(
    hasAvatar: Boolean,
    onOpenAvatar: () -> Unit,
    onAvatarPicked: () -> Unit
) {
    val dialogController = LocalDialogController.current


    AlertDialog(
        title = { Text(stringResource(R.string.edit_status)) },
        text = {

        },
        shape = DialogShape,
        onDismissRequest = { dialogController.close() },
        icon = { Icon(Icons.Outlined.Edit, null) },
        confirmButton = {
            TextButton(
                onClick = {

                    dialogController.close()
                }
            ) {
                Text(stringResource(R.string.done))
            }
        },
        dismissButton = {
            TextButton(onClick = { dialogController.close() }) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}