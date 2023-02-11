package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.RemoveCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.presentation.recipe_post_creation.stripToDouble
import ru.tech.cookhelper.presentation.ui.theme.DialogShape
import ru.tech.cookhelper.presentation.ui.theme.ProductMeasure
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.toMutableStateMap
import ru.tech.cookhelper.presentation.ui.widgets.CozyTextField
import ru.tech.cookhelper.presentation.ui.widgets.TextFieldAppearance

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PickProductsWithMeasuresDialog(
    products: Map<Product, String>,
    allProducts: List<Product>,
    onProductsPicked: (newProducts: Map<Product, String>) -> Unit,
    onDismissRequest: () -> Unit
) {
    val localProducts = rememberSaveable(saver = MapListSaver) { products.toMutableStateMap() }

    var addingProducts by rememberSaveable { mutableStateOf(false) }
    val selectedProducts = remember { mutableStateListOf<Int>() }

    val combinedAllProducts by remember(addingProducts) {
        derivedStateOf {
            allProducts.filter { !localProducts.contains(it) }
        }
    }

    var allProductsSearch by remember { mutableStateOf("") }

    val lazyColumnModifier =
        Modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.5f)

    AlertDialog(
        shape = DialogShape,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(vertical = 16.dp),
        title = {
            AnimatedContent(
                targetState = addingProducts
            ) { picking ->
                if (!picking) {
                    SideEffect { allProductsSearch = "" }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            stringResource(R.string.change_ingredients),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 12.dp)
                        )
                        AnimatedVisibility(visible = combinedAllProducts.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    addingProducts = true
                                    selectedProducts.clear()
                                },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(Icons.Rounded.AddCircleOutline, null)
                            }
                        }
                    }
                } else {
                    CozyTextField(
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 16.sp),
                        onValueChange = { allProductsSearch = it },
                        hint = {
                            Text(stringResource(R.string.search_here))
                        },
                        value = allProductsSearch,
                        endIcon = {
                            AnimatedVisibility(
                                visible = allProductsSearch != "",
                                enter = fadeIn() + scaleIn(),
                                exit = fadeOut() + scaleOut()
                            ) {
                                IconButton(onClick = { allProductsSearch = "" }) {
                                    Icon(Icons.Rounded.Close, null)
                                }
                            }
                        }
                    )
                }
            }
        },
        text = {
            AnimatedContent(targetState = addingProducts) { picking ->
                if (!picking) {
                    if (localProducts.isNotEmpty()) {
                        Column(modifier = lazyColumnModifier) {
                            Separator()
                            LazyColumn(
                                modifier = Modifier.weight(1f, false),
                                contentPadding = PaddingValues(vertical = 4.dp),
                            ) {
                                items(localProducts.keys.toList(), key = { it.id }) {
                                    Card(
                                        modifier = Modifier
                                            .padding(vertical = 4.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                                alpha = 0.5f
                                            )
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(vertical = 12.dp)
                                                .fillMaxWidth()
                                                .clip(SquircleShape(4.dp)),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            IconButton(onClick = {
                                                localProducts.remove(it)
                                            }) {
                                                Icon(Icons.Rounded.RemoveCircleOutline, null)
                                            }
                                            Text(
                                                text = it.title,
                                                fontSize = 16.sp,
                                                modifier = Modifier
                                                    .padding(12.dp)
                                                    .weight(1f)
                                            )
                                            CozyTextField(
                                                onValueChange = { str ->
                                                    localProducts[it] = str.stripToDouble()
                                                },
                                                appearance = TextFieldAppearance.Outlined,
                                                value = localProducts[it] ?: "",
                                                textStyle = LocalTextStyle.current.copy(
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                keyboardOptions = KeyboardOptions.Default.copy(
                                                    keyboardType = KeyboardType.Number,
                                                    imeAction = ImeAction.Next
                                                ),
                                                label = { Text(it.mimetype) },
                                                modifier = Modifier
                                                    .width(TextFieldDefaults.MinHeight * 1.5f)
                                                    .padding(bottom = 8.dp)
                                                    .onFocusChanged { f ->
                                                        if (!f.isFocused) {
                                                            localProducts[it]?.removeSuffix(".")
                                                        }
                                                    }
                                            )
                                            Spacer(Modifier.width(12.dp))
                                        }
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
                                Icons.Outlined.ProductMeasure,
                                null,
                                modifier = Modifier.size(72.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                stringResource(R.string.no_ingredients_picked),
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                } else {
                    val list by remember(allProductsSearch) {
                        derivedStateOf {
                            combinedAllProducts.filter {
                                if (allProductsSearch.isNotEmpty()) it.title.lowercase()
                                    .contains(allProductsSearch.lowercase())
                                else true
                            }
                        }
                    }
                    if (list.isNotEmpty()) {
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
                                                    if (!contains(it.id)) add(it.id)
                                                    else remove(it.id)
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
                                            checked = selectedProducts.contains(it.id),
                                            onCheckedChange = { _ ->
                                                selectedProducts.apply {
                                                    if (!contains(it.id)) add(it.id)
                                                    else remove(it.id)
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
            }
        },
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = {
                if (!addingProducts) {
                    onDismissRequest()
                    onProductsPicked(localProducts)
                } else {
                    localProducts.putAll(
                        allProducts.mapNotNull {
                            if (selectedProducts.contains(it.id) && !localProducts.any { p -> p.key.id == it.id }) it
                            else null
                        }.associateWith { "" }
                    )
                    addingProducts = false
                }
            }) {
                Text(stringResource(if (!addingProducts) R.string.ok else R.string.apply))
            }
        }
    )
}

private val MapListSaver = Saver<SnapshotStateMap<Product, String>, String>(
    save = { it.entries.joinToString("#") { p -> "${p.key.id}_${p.key.title}_${p.key.category}_${p.key.mimetype}-${p.value}" } },
    restore = {
        val map = mutableStateMapOf<Product, String>()
        it.split("#").mapNotNull { str ->
            val data = str.split("-")
            val productData = data[0].split("_")
            try {
                val key = Product(
                    id = productData[0].toInt(),
                    title = productData[1],
                    category = productData[2].toInt(),
                    mimetype = productData[3]
                )
                val value = data[1]
                map[key] = value
            } catch (e: Exception) {
                null
            }
        }
        map
    }
)