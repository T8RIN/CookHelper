package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.DialogShape

@Composable
fun LeaveUnsavedDataDialog(
    title: Int,
    message: Int,
    onLeave: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        shape = DialogShape,
        title = { Text(stringResource(title), textAlign = TextAlign.Center) },
        text = { Text(stringResource(message), textAlign = TextAlign.Center) },
        onDismissRequest = { onDismissRequest() },
        icon = { Icon(Icons.Outlined.Save, null) },
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.cancel))
            }
        },
        dismissButton = {
            FilledTonalButton(onClick = {
                onDismissRequest()
                onLeave()
            }) {
                Text(stringResource(R.string.leave_without_saving))
            }
        }
    )
}
