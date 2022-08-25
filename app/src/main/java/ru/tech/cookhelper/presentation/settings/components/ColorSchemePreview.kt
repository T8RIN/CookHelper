package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.app.components.TopAppBarSize
import ru.tech.cookhelper.presentation.chat.components.MessageBubbleItem
import ru.tech.cookhelper.presentation.chat.components.MessageHeader
import ru.tech.cookhelper.presentation.chat.formatOrNull
import ru.tech.cookhelper.presentation.home_screen.components.BottomNavigationBar
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.FabSize
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.createSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorSchemePreview() {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = SquircleShape(16.dp),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.surfaceVariant.createSecondaryColor(0.05f)
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        TopAppBar(
            title = {
                Text(stringResource(R.string.preview))
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
                    Icon(Icons.Rounded.Computer, null)
                }
            }
        )
        Text(
            stringResource(R.string.chat_preview),
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(12.dp)
        )
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
        Text(
            stringResource(R.string.buttons_preview),
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(12.dp)
        )
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
        }
        Spacer(Modifier.height(8.dp))
        var selectedItem by remember { mutableStateOf<Screen>(Screen.Settings) }
        BottomNavigationBar(
            selectedItem = selectedItem,
            items = listOf(
                Screen.Settings,
                Screen.ChatList,
                Screen.Profile,
                Screen.Home.None
            ),
            windowInsets = WindowInsets(0.dp),
            onClick = { selectedItem = it })
    }
}