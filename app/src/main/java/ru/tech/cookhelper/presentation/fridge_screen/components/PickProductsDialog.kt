package ru.tech.cookhelper.presentation.fridge_screen.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.presentation.app.components.CozyTextField
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.theme.DialogShape
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.rememberMutableStateListOf
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.close
import ru.tech.cookhelper.presentation.ui.utils.provider.show

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun PickProductsDialog(
    allProducts: List<Product>,
    onPicked: (products: List<Product>) -> Unit
) {
    val dialogController = LocalDialogController.current
    var searchValue by rememberSaveable { mutableStateOf("") }

    val selectedProducts = ProductsSaver.rememberMutableStateListOf()

    val lazyColumnModifier =
        Modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.5f)

    AlertDialog(
        shape = DialogShape,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(vertical = 16.dp),
        title = {
            CozyTextField(
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 16.sp),
                onValueChange = { searchValue = it },
                hint = {
                    Text(stringResource(R.string.search_here))
                },
                value = searchValue,
                endIcon = {
                    AnimatedVisibility(
                        visible = searchValue != "",
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        IconButton(onClick = { searchValue = "" }) {
                            Icon(Icons.Rounded.Close, null)
                        }
                    }
                }
            )
        },
        text = {
            val list by remember(searchValue) {
                derivedStateOf {
                    allProducts.filter {
                        if (allProducts.isNotEmpty()) it.title.lowercase()
                            .contains(searchValue.lowercase())
                        else true
                    }
                }
            }
            AnimatedContent(targetState = list.isEmpty()) { empty ->
                if (!empty) {
                    Column(lazyColumnModifier) {
                        Separator()
                        LazyColumn(
                            modifier = Modifier.weight(1f, false),
                            contentPadding = PaddingValues(vertical = 4.dp),
                        ) {
                            items(list, key = { it.id }) {
                                Row(
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .fillMaxWidth()
                                        .clip(SquircleShape(4.dp))
                                        .clickable {
                                            selectedProducts.apply {
                                                if (!contains(it)) add(it)
                                                else remove(it)
                                            }
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = it.title,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .weight(1f)
                                    )
                                    Checkbox(
                                        checked = selectedProducts.contains(it),
                                        onCheckedChange = { _ ->
                                            selectedProducts.apply {
                                                if (!contains(it)) add(it)
                                                else remove(it)
                                            }
                                        }
                                    )
                                    Spacer(Modifier.width(12.dp))
                                }
                            }
                        }
                        Separator()
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(16.dp))
                        Icon(
                            Icons.Outlined.SearchOff,
                            null,
                            modifier = Modifier.size(72.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            stringResource(R.string.nothing_found_by_the_search),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        },
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = {
                    onPicked(selectedProducts.toList())
                    dialogController.close()
                }
            ) {
                Text(stringResource(R.string.add))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    if(selectedProducts.isNotEmpty()) {
                        dialogController.show(
                            Dialog.LeaveUnsavedData(
                                title = R.string.picking_products,
                                message = R.string.picking_products_started,
                                onLeave = { dialogController.close() }
                            )
                        )
                    } else {
                        dialogController.close()
                    }
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )

}

private val ProductsSaver = Saver<SnapshotStateList<Product>, String>(
    save = { it.joinToString("@") { p -> "${p.id}-${p.title}-${p.category}-${p.mimetype}" } },
    restore = {
        it.split("@").mapNotNull { str ->
            val data = str.split("-")
            try {
                Product(
                    id = data[0].toInt(),
                    title = data[1],
                    category = data[2],
                    mimetype = data[3]
                )
            } catch (e: Exception) {
                null
            }
        }.toMutableStateList()
    }
)