package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.DialogShape

@Composable
fun PickLanguageDialog(
    entries: Map<String, String>,
    selected: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(onDismissRequest = onDismiss,
        shape = DialogShape,
        icon = { Icon(imageVector = Icons.Outlined.Translate, contentDescription = null) },
        title = { Text(stringResource(R.string.language)) },
        text = {
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                entries.forEach { locale ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.small)
                            .clickable {
                                onSelect(locale.key)
                                onDismiss()
                            }
                            .padding(start = 12.dp, end = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected == locale.value,
                            onClick = {
                                onSelect(locale.key)
                                onDismiss()
                            }
                        )
                        Text(locale.value)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}