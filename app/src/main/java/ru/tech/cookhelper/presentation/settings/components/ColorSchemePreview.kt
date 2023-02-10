package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BreakfastDining
import androidx.compose.material.icons.rounded.BreakfastDining
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.constants.Constants.LOREM_IPSUM
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.app.components.*
import ru.tech.cookhelper.presentation.chat.components.MessageBubbleItem
import ru.tech.cookhelper.presentation.chat.components.MessageHeader
import ru.tech.cookhelper.presentation.chat.formatOrNull
import ru.tech.cookhelper.presentation.fridge_screen.components.ProductItem
import ru.tech.cookhelper.presentation.home_screen.components.BottomNavigationBar
import ru.tech.cookhelper.presentation.profile.components.UserInfoBlock
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.FabSize
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.theme.invoke
import ru.tech.cookhelper.presentation.ui.theme.mixedColor
import ru.tech.cookhelper.presentation.ui.theme.onMixedColor
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHostState
import ru.tech.cookhelper.presentation.ui.widgets.AppBarTitle
import ru.tech.cookhelper.presentation.ui.widgets.TopAppBar
import ru.tech.cookhelper.presentation.ui.widgets.TopAppBarSize

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ColorSchemePreview() {
    var selectedItem by rememberSaveable { mutableStateOf<Screen>(Screen.Buttons) }
    val toastHost = LocalToastHostState.current
    var showSampleDialog by rememberSaveable { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = SquircleShape(16.dp),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        TopAppBar(
            title = {
                AppBarTitle(targetState = selectedItem.shortTitle.asString()) {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            topAppBarSize = TopAppBarSize.Centered,
            navigationIcon = {
                val testMessage = stringResource(R.string.toast_preview)
                Icons.Rounded.BreakfastDining(
                    onClick = {
                        toastHost.show(Icons.Outlined.BreakfastDining, testMessage)
                    }
                )
            },
            windowInsets = WindowInsets(0.dp),
            background = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            actions = {
                Icons.Rounded.Newspaper(
                    onClick = { showSampleDialog = true }
                )
            }
        )
        AnimatedContent(selectedItem) {
            Column {
                when (it) {
                    is Screen.ChatList -> ChatBlock()
                    is Screen.Buttons -> ButtonsBlock()
                    is Screen.Home.Fridge -> FridgeBlock()
                    is Screen.Profile -> ProfileBlock()
                    else -> Unit
                }
            }
        }
        BottomNavigationBar(
            selectedItem = selectedItem,
            items = listOf(
                Screen.Buttons,
                Screen.ChatList,
                Screen.Profile,
                Screen.Home.Fridge
            ),
            windowInsets = WindowInsets(0.dp),
            onClick = { selectedItem = it })
    }

    if (showSampleDialog) {
        AlertDialog(
            onDismissRequest = { showSampleDialog = false },
            icon = { Icons.Rounded.Newspaper() },
            confirmButton = {
                TextButton(onClick = { showSampleDialog = false }) {
                    Text(stringResource(R.string.ok))
                }
            },
            title = { Text(stringResource(R.string.preview)) },
            text = { Text(LOREM_IPSUM) }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun FridgeBlock() {
    val title = stringResource(R.string.product)
    val lists = remember {
        listOf(
            List(7) {
                Product(it, title, it + 1, "")
            },
            List(7) {
                Product(it, title, it + 8, "")
            },
            List(7) {
                Product(it, title, it + 15, "")
            }
        )
    }
    val state = rememberPagerState()
    Box {
        HorizontalPager(count = lists.size, state = state) {
            Column {
                lists[it].forEachIndexed { index, product ->
                    Box {
                        val color = MaterialTheme.colorScheme.outlineVariant
                        val density = LocalDensity.current
                        val width = remember(density) { with(density) { 1.dp.toPx() } }
                        ProductItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    if (it != 2) {
                                        drawRect(
                                            color,
                                            size = this.size.copy(width = width),
                                            topLeft = Offset(x = this.size.width, y = 0f)
                                        )
                                    }
                                }
                                .padding(start = 8.dp),
                            product = product,
                            onDelete = {}
                        )
                    }
                    Separator()
                    if (index == lists[it].lastIndex) Spacer(Modifier.height(8.dp))
                }
                Spacer(Modifier.height(16.dp))
            }
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            pagerState = state
        )
    }
}

@Composable
private fun ChatBlock() {
    Spacer(Modifier.height(8.dp))
    MessageHeader(
        text = formatOrNull(
            System.currentTimeMillis(),
            "d MMMM"
        ).toString()
    )
    MessageBubbleItem(
        isMessageFromCurrentUser = true,
        text = stringResource(R.string.hello_world_preview),
        timestamp = System.currentTimeMillis(),
        cutTopCorner = true,
        showPointingArrow = true
    )
    Spacer(Modifier.height(4.dp))
    MessageBubbleItem(
        isMessageFromCurrentUser = false,
        text = stringResource(R.string.hello_world_answer_preview),
        timestamp = System.currentTimeMillis(),
        cutTopCorner = true,
        showPointingArrow = true
    )
    Spacer(Modifier.height(4.dp))
    MessageBubbleItem(
        isMessageFromCurrentUser = true,
        text = stringResource(R.string.what_we_are_cooking_answer_preview),
        timestamp = System.currentTimeMillis(),
        cutTopCorner = true,
        showPointingArrow = true
    )
    Spacer(Modifier.height(4.dp))
    MessageBubbleItem(
        isMessageFromCurrentUser = false,
        text = stringResource(R.string.yummy_preview),
        timestamp = System.currentTimeMillis(),
        cutTopCorner = true,
        showPointingArrow = true
    )
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun ButtonsBlock() {
    Spacer(Modifier.height(8.dp))
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        mainAxisAlignment = FlowMainAxisAlignment.Center,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp
    ) {
        OutlinedButton(onClick = {}) {
            Text(stringResource(R.string.preview))
        }
        FilledTonalButton(onClick = {}) {
            Text(stringResource(R.string.preview))
        }
        Button(onClick = {}) {
            Text(stringResource(R.string.preview))
        }
        ElevatedButton(onClick = {}) {
            Text(stringResource(R.string.preview))
        }
        ExpandableFloatingActionButton(
            size = FabSize.Small,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            icon = {
                Icon(Icons.Rounded.Edit, null, Modifier.size(it))
            }, onClick = {}
        )
        ExpandableFloatingActionButton(
            icon = {
                Icon(Icons.Rounded.Edit, null, Modifier.size(it))
            },
            expanded = true,
            text = { Text(stringResource(R.string.preview)) },
            onClick = {}
        )
        ExpandableFloatingActionButton(
            icon = {
                Icon(Icons.Rounded.Edit, null, Modifier.size(it))
            },
            containerColor = MaterialTheme.colorScheme.mixedColor,
            contentColor = MaterialTheme.colorScheme.onMixedColor,
            onClick = {}
        )
        Spacer(Modifier.width(8.dp))
        var checked by remember { mutableStateOf(false) }
        Switch(checked = checked, onCheckedChange = { checked = !checked })
        var selected by remember { mutableStateOf(0) }
        Row {
            RadioButton(selected = selected == 0, onClick = { selected = 0 })
            RadioButton(selected = selected == 1, onClick = { selected = 1 })
            RadioButton(selected = selected == 2, onClick = { selected = 2 })
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun ProfileBlock() {
    Spacer(Modifier.height(8.dp))
    UserInfoBlock(
        userState = UserState(
            User(
                0,
                email = "",
                name = stringResource(R.string.preview),
                lastSeen = System.currentTimeMillis(),
                nickname = "",
                surname = ""
            )
        ),
        onEdit = {},
        onAvatarClick = {},
        onStatusUpdate = {}
    )
    Spacer(Modifier.height(8.dp))
    Divider()
    Spacer(Modifier.width(8.dp))
    Row {
        val testMessage = stringResource(R.string.snackbar_preview)
        val ok = stringResource(R.string.ok)

        Spacer(
            Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Snackbar(
            snackbarData = object : SnackbarData {
                override val visuals: SnackbarVisuals
                    get() = object : SnackbarVisuals {
                        override val actionLabel: String
                            get() = ok
                        override val duration: SnackbarDuration
                            get() = SnackbarDuration.Indefinite
                        override val message: String
                            get() = testMessage
                        override val withDismissAction: Boolean
                            get() = false

                    }

                override fun dismiss() {}

                override fun performAction() {}
            }
        )
        Spacer(
            Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
    }
    Spacer(Modifier.width(8.dp))
    Divider()
    Spacer(Modifier.width(8.dp))
    Row {
        val testMessage = stringResource(R.string.toast_preview)
        Spacer(
            Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Toast(
            modifier = Modifier
                .heightIn(min = 48.dp)
                .padding(top = 8.dp)
                .alpha(0.95f),
            toastData = object : ToastData {
                override val visuals: ToastVisuals
                    get() = object : ToastVisuals {
                        override val message: String
                            get() = testMessage
                        override val icon: ImageVector
                            get() = Icons.Outlined.BreakfastDining
                        override val duration: ToastDuration
                            get() = ToastDuration.Long
                    }

                override fun dismiss() {}
            }
        )
        Spacer(
            Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
    }
    Spacer(Modifier.height(8.dp))
}