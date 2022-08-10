package ru.tech.cookhelper.presentation.recipe_post_creation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.LazyTextField
import ru.tech.cookhelper.presentation.recipe_post_creation.viewModel.RecipePostCreationViewModel
import ru.tech.cookhelper.presentation.ui.theme.ProductMeasure
import ru.tech.cookhelper.presentation.ui.utils.Dialog
import ru.tech.cookhelper.presentation.ui.utils.clearState
import ru.tech.cookhelper.presentation.ui.utils.name
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.show
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
)
@Composable
fun RecipePostCreationScreen(
    viewModel: RecipePostCreationViewModel = scopedViewModel(),
    onBack: () -> Unit
) {
    val focus = LocalFocusManager.current
    var doneEnabled by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    val dialogController = LocalDialogController.current

    LaunchedEffect(scrollState) {
        var prev = 0
        snapshotFlow {
            scrollState.value
        }.collect {
            fabExpanded = it <= prev
            prev = it
        }
    }

    val user = viewModel.user.value

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
        label, imageUri, time, calories, proteins, fats, carbohydrates, category, steps
    )

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { uri ->
                imageUri = uri.toString()
            }
        }
    )

    val goBack = {
        if (dataList.any { it != "" }) {
            dialogController.show(
                Dialog.LeaveUnsavedData(
                    title = R.string.recipe_creation_started,
                    message = R.string.recipe_creation_started_leave_message,
                    onLeave = { onBack() }
                )
            )
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
            Modifier
                .imePadding()
                .navigationBarsPadding()) {
            TopAppBar(
                background = TopAppBarDefaults
                    .smallTopAppBarColors()
                    .containerColor(1f)
                    .value,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Picture(model = user?.avatar, modifier = Modifier.size(40.dp))
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
                                label,
                                imageUri,
                                time,
                                calories,
                                proteins,
                                fats,
                                carbohydrates,
                                category,
                                steps
                            )
                        },
                        enabled = doneEnabled,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Rounded.Done, null)
                    }
                }
            )

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyTextField(
                        value = label,
                        onValueChange = { label = it },
                        startIcon = Icons.Outlined.FontDownload,
                        label = stringResource(R.string.recipe_headline),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = LocalTextStyle.current.fontSize
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        onClick = { resultLauncher.launch("image/*") },
                        modifier = Modifier
                            .height(TextFieldDefaults.MinHeight * 3.5f)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        AnimatedContent(targetState = imageUri) { uri ->
                            if (uri.isEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
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
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                Box {
                                    Picture(
                                        model = uri,
                                        modifier = Modifier.fillMaxSize(),
                                        shape = RectangleShape
                                    )
                                    FilledIconButton(
                                        onClick = { resultLauncher.launch("image/*") },
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
                    LazyTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.products.value.joinToString(
                            separator = "\n",
                            transform = { "${it.name} - ${it.amount} ${it.mimeType}" }
                        ).trim(),
                        onValueChange = {},
                        shape = RoundedCornerShape(24.dp),
                        readOnly = true,
                        singleLine = false,
                        label = stringResource(R.string.ingredients),
                        startIcon = Icons.Outlined.ProductMeasure
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        LazyTextField(
                            value = time,
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
                        LazyTextField(
                            value = calories,
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
                        LazyTextField(
                            value = proteins,
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
                        LazyTextField(
                            value = fats,
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
                    LazyTextField(
                        value = carbohydrates,
                        startIcon = Icons.Outlined.Cake,
                        onValueChange = { carbohydrates = it },
                        label = stringResource(R.string.carbohydrates),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        formatText = { stripToDouble() },
                        onLoseFocusTransformation = { removeSuffix(".") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = false,
                        label = stringResource(R.string.category),
                        startIcon = Icons.Outlined.Category,
                        endIcon = {
                            IconButton(onClick = {
                                dialogController.show(
                                    Dialog.CategorySelection(
                                        categories = viewModel.categories.value,
                                        onCategorySelected = {
                                            category = it
                                        },
                                        selectedCategory = category
                                    )
                                )
                            }) {
                                Icon(Icons.Rounded.Edit, null)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyTextField(
                        value = steps,
                        startIcon = Icons.Outlined.Notes,
                        onValueChange = { steps = it },
                        label = stringResource(R.string.step_by_step_recipe),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 88.dp),
                        shape = RoundedCornerShape(
                            topStart = 4.dp,
                            topEnd = 4.dp,
                            bottomStart = 24.dp,
                            bottomEnd = 24.dp
                        ),
                        singleLine = false,
                        keyboardOptions = KeyboardOptions.Default
                    )
                }
            }
        }

        ExpandableFloatingActionButton(
            onClick = {
                dialogController.show(
                    Dialog.PickProductsWithMeasures(
                        products = viewModel.products.value,
                        allProducts = viewModel.allProducts.value,
                        onProductsPicked = { newProducts ->
                            viewModel.setProducts(newProducts)
                        }
                    )
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .align(Alignment.BottomEnd),
            expanded = fabExpanded,
            icon = { Icon(Icons.Outlined.EggAlt, null) },
            text = { Text(stringResource(R.string.change_ingredients)) }
        )

        LaunchedEffect(imageUri) {
            doneEnabled = !dataList.contains("")
        }

    }

    BackHandler { goBack() }

    DisposableEffect(Unit) {
        onDispose {
            clearState(key = Dialog.CategorySelection::class.name)
            clearState(key = Dialog.PickProductsWithMeasures::class.name)
        }
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
