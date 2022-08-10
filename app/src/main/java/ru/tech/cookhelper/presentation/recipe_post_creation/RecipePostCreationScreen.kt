package ru.tech.cookhelper.presentation.recipe_post_creation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
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
    goBack: () -> Unit
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
    var categoryText by remember { mutableStateOf("") }

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { uri ->
                imageUri = uri.toString()
            }
        }
    )

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focus.clearFocus() })
            }
    ) {
        Column(Modifier.imePadding()) {
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
                            /*TODO: Post saving */
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
                        onValueChange = {},
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
                            .height(TextFieldDefaults.MinHeight * 4)
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
                    Row {
                        LazyTextField(
                            startIcon = Icons.Outlined.AvTimer,
                            onValueChange = {},
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
                            startIcon = Icons.Outlined.Restaurant,
                            onValueChange = {},
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
                            startIcon = Icons.Outlined.Egg,
                            onValueChange = {},
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
                            startIcon = Icons.Outlined.OilBarrel,
                            onValueChange = {},
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
                        startIcon = Icons.Outlined.Cake,
                        onValueChange = {},
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

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                focus.clearFocus()
                            }
                            .animateContentSize(),
                        value = categoryText,
                        onValueChange = {},
                        colors = TextFieldDefaults.textFieldColors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        readOnly = true,
                        shape = RoundedCornerShape(4.dp),
                        label = { Text(stringResource(R.string.category), modifier = Modifier.offset(4.dp)) },
                        leadingIcon = { Icon(Icons.Outlined.Category, null) },
                        trailingIcon = {
                            IconButton(onClick = {
                                dialogController.show(
                                    Dialog.CategorySelection(
                                        categories = viewModel.categories.value,
                                        onCategorySelected = {
                                            categoryText = it
                                        },
                                        selectedCategory = categoryText
                                    )
                                )
                            }) {
                                Icon(Icons.Rounded.Edit, null)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyTextField(
                        startIcon = Icons.Outlined.Notes,
                        onValueChange = {},
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
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .align(Alignment.BottomEnd),
            expanded = fabExpanded,
            icon = { Icon(Icons.Outlined.EggAlt, null) },
            text = { Text(stringResource(R.string.change_products)) }
        )

        LaunchedEffect(imageUri) {
            doneEnabled = imageUri.isNotEmpty()
        }

    }

    BackHandler { goBack() }

    DisposableEffect(Unit) {
        onDispose {
            clearState(key = Dialog.CategorySelection::class.name)
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
