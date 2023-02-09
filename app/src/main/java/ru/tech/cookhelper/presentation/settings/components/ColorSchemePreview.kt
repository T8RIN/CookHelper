package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.app.components.UserState
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
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.widgets.AppBarTitle
import ru.tech.cookhelper.presentation.ui.widgets.TopAppBar
import ru.tech.cookhelper.presentation.ui.widgets.TopAppBarSize

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ColorSchemePreview() {
    var selectedItem by remember { mutableStateOf<Screen>(Screen.Buttons) }

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
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.ArrowBack, null)
                }
            },
            windowInsets = WindowInsets(0.dp),
            background = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.Newspaper, null)
                }
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
}

@Composable
private fun FridgeBlock() {
    val title = stringResource(R.string.product)
    val list = remember {
        List(21) {
            Product(it, title, it + 1, "")
        }
    }
    Column(
        Modifier
            .height(400.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(8.dp))
        list.forEachIndexed { index, product ->
            ProductItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                product = product,
                onDelete = {}
            )
            if (index != list.lastIndex) Separator()
        }
        Spacer(Modifier.height(8.dp))
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
            containerColor = MaterialTheme.colorScheme.surface,
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
}