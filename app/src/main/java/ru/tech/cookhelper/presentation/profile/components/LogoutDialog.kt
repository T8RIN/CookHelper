package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.DialogShape

@Composable
fun LogoutDialog(onLogout: () -> Unit, onDismissRequest: () -> Unit) {
    AlertDialog(
        title = { Text(stringResource(R.string.account_log_out)) },
        text = {
            Text(
                stringResource(R.string.log_out_message),
                textAlign = TextAlign.Center
            )
        },
        shape = DialogShape,
        onDismissRequest = { onDismissRequest() },
        icon = { Icon(Icons.Outlined.Logout, null) },
        confirmButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.stay))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onLogout()
                onDismissRequest()
            }) {
                Text(stringResource(R.string.exit))
            }
        }
    )
}