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
import androidx.compose.ui.ExperimentalComposeUiApi
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
import ru.tech.cookhelper.presentation.ui.theme.ProductMeasure
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.computedStateOf
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.close

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PickProductsWithMeasuresDialog(
    products: List<Product>,
    allProducts: List<Product>,
    onProductsPicked: (newProducts: List<Product>) -> Unit
) {
    var localProducts by rememberSaveable(saver = ProductsSaver) { mutableStateOf(products) }
    val localAmounts = rememberSaveable(saver = MapListSaver) { mutableStateMapOf() }

    LaunchedEffect(Unit) {
        products.forEach {
            localAmounts[it.id] = it.amount.toString()
        }
    }

    val dialogController = LocalDialogController.current

    var addingProducts by rememberSaveable { mutableStateOf(false) }
    val selectedProducts = remember { mutableStateListOf<Int>() }

    val combinedAllProducts by computedStateOf(addingProducts) {
        allProducts.filter { !localProducts.contains(it) }
    }

    var allProductsSearch by remember { mutableStateOf("") }

    val lazyColumnModifier =
        Modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.5f)

    AlertDialog(
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
                    LazyTextField(
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 16.sp),
                        onValueChange = { allProductsSearch = it },
                        hint = stringResource(R.string.search_here),
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
                                items(localProducts) {
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
                                                localProducts = localProducts
                                                    .toMutableList()
                                                    .apply { removeIf { p -> p.id == it.id } }
                                                    .also { _ -> localAmounts.remove(it.id) }
                                            }) {
                                                Icon(Icons.Rounded.RemoveCircleOutline, null)
                                            }
                                            Text(
                                                text = it.name,
                                                fontSize = 16.sp,
                                                modifier = Modifier
                                                    .padding(12.dp)
                                                    .weight(1f)
                                            )
                                            OutlinedTextField(
                                                onValueChange = { str ->
                                                    localAmounts[it.id] = str.stripToDouble()
                                                },
                                                value = localAmounts[it.id] ?: "",
                                                textStyle = LocalTextStyle.current.copy(
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                keyboardOptions = KeyboardOptions.Default.copy(
                                                    keyboardType = KeyboardType.Number,
                                                    imeAction = ImeAction.Next
                                                ),
                                                singleLine = true,
                                                label = { Text(it.mimeType) },
                                                modifier = Modifier
                                                    .width(TextFieldDefaults.MinHeight * 1.5f)
                                                    .padding(bottom = 8.dp)
                                                    .onFocusChanged { f ->
                                                        if (!f.isFocused) localAmounts[it.id] =
                                                            (localAmounts[it.id]
                                                                ?: "").removeSuffix(".")
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
                    val list = combinedAllProducts.filter {
                        if (allProductsSearch.isNotEmpty()) it.name.lowercase()
                            .contains(allProductsSearch.lowercase())
                        else true
                    }
                    if (list.isNotEmpty()) {
                        Column(lazyColumnModifier) {
                            Separator()
                            LazyColumn(
                                modifier = Modifier.weight(1f, false),
                                contentPadding = PaddingValues(vertical = 4.dp),
                            ) {
                                items(list) {
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
                                            text = it.name,
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
                    dialogController.close()
                    onProductsPicked(
                        localProducts.map {
                            val amount = localAmounts[it.id]
                            it.copy(
                                amount = amount?.toFloatOrNull() ?: amount?.toIntOrNull()?.toFloat()
                                ?: 0f
                            )
                        }
                    )
                } else {
                    localProducts = localProducts + allProducts.mapNotNull {
                        if (selectedProducts.contains(it.id) && !localProducts.any { p -> p.id == it.id }) it
                        else null
                    }
                    addingProducts = false
                }
            }) {
                Text(stringResource(if (!addingProducts) R.string.okay else R.string.apply))
            }
        }
    )
}

private val ProductsSaver = Saver<MutableState<List<Product>>, String>(
    save = { it.value.joinToString("@") { p -> "${p.id}-${p.name}-${p.amount}-${p.mimeType}" } },
    restore = {
        mutableStateOf(
            it.split("@").mapNotNull { str ->
                val data = str.split("-")
                try {
                    Product(
                        id = data[0].toIntOrNull() ?: 0,
                        name = data[1],
                        amount = data[2].toFloatOrNull() ?: 0f,
                        mimeType = data[3]
                    )
                } catch (e: Exception) {
                    null
                }
            }
        )
    }
)

private val MapListSaver = Saver<SnapshotStateMap<Int, String>, String>(
    save = { it.entries.joinToString("#") { p -> "${p.key}-${p.value}" } },
    restore = {
        val map = mutableStateMapOf<Int, String>()
        it.split("#").mapNotNull { str ->
            val data = str.split("-")
            try {
                val key = data[0].toIntOrNull() ?: 0
                val value = data[1]
                map[key] = value
            } catch (e: Exception) {
                null
            }
        }
        map
    }
)
