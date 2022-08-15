package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.close
import ru.tech.cookhelper.presentation.ui.utils.provider.currentDialog

@Composable
fun CategorySelectionDialog(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val dialogController = LocalDialogController.current

    AlertDialog(
        title = { Text(stringResource(R.string.choose_category)) },
        text = {
            Column {
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                LazyColumn(
                    modifier = Modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.35f),
                ) {
                    items(categories) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    onCategorySelected(it)
                                    dialogController.close()
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .weight(1f)
                            )
                            if (selectedCategory == it) {
                                Icon(
                                    Icons.Rounded.Done,
                                    null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(Modifier.width(12.dp))
                            }
                        }
                    }
                }
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        },
        onDismissRequest = { dialogController.close() },
        icon = { Icon(dialogController.currentDialog.icon, null) },
        confirmButton = {
            TextButton(onClick = { dialogController.close() }) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}