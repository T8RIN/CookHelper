package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.DialogShape
import ru.tech.cookhelper.presentation.ui.widgets.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStatusDialog(
    currentStatus: String,
    onDone: (newStatus: String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var status by rememberSaveable { mutableStateOf(currentStatus) }
    val textStyle = LocalTextStyle.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .width(TextFieldDefaults.MinWidth + 40.dp)
            .padding(vertical = 16.dp),
        title = { Text(stringResource(R.string.edit_status)) },
        text = {
            Column {
                Spacer(Modifier.height(4.dp))
                CompositionLocalProvider(LocalTextStyle provides textStyle) {
                    CozyTextField(
                        value = status,
                        appearance = TextFieldAppearance.Outlined,
                        onValueChange = {
                            status = it
                        },
                        label = {
                            Text(stringResource(R.string.status))
                        }
                    )
                }
                Spacer(Modifier.height(4.dp))
            }
        },
        shape = DialogShape,
        onDismissRequest = { onDismissRequest() },
        icon = { Icon(Icons.Outlined.Edit, null) },
        confirmButton = {
            Button(
                onClick = {
                    onDone(status)
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.done))
            }
        },
        dismissButton = {
            FilledTonalButton(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}