package ru.tech.cookhelper.presentation.recipe_post_creation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.getLastAvatar
import ru.tech.cookhelper.presentation.recipe_post_creation.components.CategorySelectionDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.LeaveUnsavedDataDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.PickProductsWithMeasuresDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.viewModel.RecipePostCreationViewModel
import ru.tech.cookhelper.presentation.ui.theme.ProductMeasure
import ru.tech.cookhelper.presentation.ui.utils.compose.PaddingUtils.addPadding
import ru.tech.cookhelper.presentation.ui.utils.compose.ScrollUtils.isScrollingUp
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Picture
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.goBack
import ru.tech.cookhelper.presentation.ui.widgets.CozyTextField
import ru.tech.cookhelper.presentation.ui.widgets.TopAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RecipePostCreationScreen(
    viewModel: RecipePostCreationViewModel = hiltViewModel()
) {
    val controller = LocalScreenController.current
    val onBack: () -> Unit = { controller.goBack() }

    val focus = LocalFocusManager.current
    var doneEnabled by rememberSaveable { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()

    var showPickProductsDialog by rememberSaveable { mutableStateOf(false) }
    var showCategorySelectionDialog by rememberSaveable { mutableStateOf(false) }
    var showLeaveUnsavedDataDialog by rememberSaveable { mutableStateOf(false) }

    val user = viewModel.user

    var imageUri by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var label by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }
    var calories by rememberSaveable { mutableStateOf("") }
    var proteins by rememberSaveable { mutableStateOf("") }
    var fats by rememberSaveable { mutableStateOf("") }
    var carbohydrates by rememberSaveable { mutableStateOf("") }
    var steps by rememberSaveable { mutableStateOf("") }

    val dataList = listOf(
        label,
        imageUri,
        time,
        calories,
        proteins,
        fats,
        carbohydrates,
        category,
        steps,
        viewModel.products.asIterable().joinToString()
    )

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let { uri ->
                imageUri = uri.toString()
            }
        }
    )

    val goBack = {
        if (dataList.any { it != "" }) {
            showLeaveUnsavedDataDialog = true
        } else onBack()
    }

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focus.clearFocus() })
            }
    ) {
        Column(
            Modifier.imePadding()
        ) {
            TopAppBar(
                background = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Picture(
                            model = user?.getLastAvatar(),
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "${user?.name?.trim()} ${user?.surname?.trim()}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(Icons.Rounded.Close, null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.sendRecipePost(
                                label = label,
                                imageUri = imageUri,
                                time = time,
                                calories = calories,
                                proteins = proteins,
                                fats = fats,
                                carbohydrates = carbohydrates,
                                category = category,
                                steps = steps
                            )
                        },
                        enabled = doneEnabled,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Rounded.Done, null)
                    }
                }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = WindowInsets.navigationBars.asPaddingValues()
                    .addPadding(bottom = 88.dp),
                state = lazyListState
            ) {
                item {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        CozyTextField(
                            value = label,
                            onValueChange = { label = it },
                            startIcon = Icons.Outlined.FontDownload,
                            label = stringResource(R.string.recipe_headline),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            textStyle = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = LocalTextStyle.current.fontSize
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            onClick = {
                                resultLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            AnimatedContent(targetState = imageUri) { uri ->
                                if (uri.isEmpty()) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(TextFieldDefaults.MinHeight * 3.5f)
                                            .padding(8.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            Icons.Rounded.AddCircle,
                                            null,
                                            modifier = Modifier.size(48.dp)
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            stringResource(R.string.add_image),
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    Box {
                                        Picture(
                                            model = uri,
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RectangleShape
                                        )
                                        FilledIconButton(
                                            onClick = {
                                                resultLauncher.launch(
                                                    PickVisualMediaRequest(
                                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                                    )
                                                )
                                            },
                                            modifier = Modifier.align(Alignment.TopEnd),
                                            colors = IconButtonDefaults.filledIconButtonColors(
                                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                                            )
                                        ) {
                                            Icon(Icons.Rounded.Autorenew, null)
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        CozyTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.products.asIterable().joinToString(
                                separator = "\n",
                                transform = { "${it.key.title} - ${it.value} ${it.key.mimetype}" }
                            ).trim(),
                            onValueChange = {},
                            shape = RoundedCornerShape(24.dp),
                            readOnly = true,
                            label = stringResource(R.string.ingredients),
                            startIcon = Icons.Outlined.ProductMeasure
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            CozyTextField(
                                value = time,
                                singleLine = true,
                                startIcon = Icons.Outlined.AvTimer,
                                onValueChange = { time = it },
                                label = stringResource(R.string.time),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                ),
                                shape = RoundedCornerShape(
                                    topStart = 24.dp,
                                    topEnd = 4.dp,
                                    bottomStart = 4.dp,
                                    bottomEnd = 4.dp
                                ),
                                formatText = {
                                    filter { it.isDigit() }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CozyTextField(
                                value = calories,
                                singleLine = true,
                                startIcon = Icons.Outlined.Restaurant,
                                onValueChange = { calories = it },
                                label = stringResource(R.string.calories),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                ),
                                shape = RoundedCornerShape(
                                    topStart = 4.dp,
                                    topEnd = 24.dp,
                                    bottomStart = 4.dp,
                                    bottomEnd = 4.dp
                                ),
                                formatText = { stripToDouble() },
                                onLoseFocusTransformation = { removeSuffix(".") }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            CozyTextField(
                                value = proteins,
                                singleLine = true,
                                startIcon = Icons.Outlined.Egg,
                                onValueChange = { proteins = it },
                                label = stringResource(R.string.proteins),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                ),
                                formatText = { stripToDouble() },
                                onLoseFocusTransformation = { removeSuffix(".") }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CozyTextField(
                                value = fats,
                                singleLine = true,
                                startIcon = Icons.Outlined.OilBarrel,
                                onValueChange = { fats = it },
                                label = stringResource(R.string.fats),
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                ),
                                formatText = { stripToDouble() },
                                onLoseFocusTransformation = { removeSuffix(".") }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        CozyTextField(
                            value = carbohydrates,
                            startIcon = Icons.Outlined.Cake,
                            onValueChange = { carbohydrates = it },
                            label = stringResource(R.string.carbohydrates),
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            formatText = { stripToDouble() },
                            onLoseFocusTransformation = { removeSuffix(".") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CozyTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            label = stringResource(R.string.category),
                            startIcon = Icons.Outlined.Category,
                            endIcon = {
                                IconButton(onClick = { showCategorySelectionDialog = true }) {
                                    Icon(Icons.Rounded.Edit, null)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CozyTextField(
                            value = steps,
                            startIcon = Icons.Outlined.Notes,
                            onValueChange = { steps = it },
                            label = stringResource(R.string.step_by_step_recipe),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp,
                                bottomStart = 24.dp,
                                bottomEnd = 24.dp
                            ),
                            keyboardOptions = KeyboardOptions.Default
                        )
                    }
                }
            }
        }

        ExpandableFloatingActionButton(
            onClick = { showPickProductsDialog = true },
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .align(Alignment.BottomEnd),
            expanded = lazyListState.isScrollingUp(),
            icon = { Icon(Icons.Outlined.EggAlt, null) },
            text = { Text(stringResource(R.string.change_ingredients)) }
        )

        LaunchedEffect(dataList) {
            doneEnabled = !dataList.contains("")
        }

    }

    BackHandler { goBack() }

    if (showPickProductsDialog) {
        PickProductsWithMeasuresDialog(
            products = viewModel.products,
            allProducts = viewModel.allProducts,
            onProductsPicked = { newProducts ->
                viewModel.setProducts(newProducts)
            },
            onDismissRequest = {
                showPickProductsDialog = false
            }
        )
    } else if (showLeaveUnsavedDataDialog) {
        LeaveUnsavedDataDialog(
            title = R.string.recipe_creation_started,
            message = R.string.recipe_creation_started_leave_message,
            onLeave = { onBack() },
            onDismissRequest = {
                showLeaveUnsavedDataDialog = false
            }
        )
    } else if (showCategorySelectionDialog) {
        CategorySelectionDialog(
            categories = viewModel.categories,
            onCategorySelected = {
                category = it
            },
            selectedCategory = category,
            onDismissRequest = {
                showCategorySelectionDialog = false
            }
        )
    }

}

fun String.stripToDouble(): String {
    var text = this
    split(".").apply {
        var tmp = getOrElse(0) { "" }
        if (tmp.isNotEmpty() && size > 1) {
            tmp += ("." + get(1))
        }
        text = tmp
    }
    return text.filter { it.isDotOrDigit() }
}

private fun Char.isDotOrDigit() = isDigit() || equals('.')
