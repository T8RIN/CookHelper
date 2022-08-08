package ru.tech.cookhelper.presentation.post_creation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.post_creation.components.PostCreationModule
import ru.tech.cookhelper.presentation.post_creation.components.PostCreationViewModel
import ru.tech.cookhelper.presentation.post_creation.components.PostType
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCreationScreen(
    viewModel: PostCreationViewModel = scopedViewModel(),
    type: PostType,
    goBack: () -> Unit
) {
    val focus = LocalFocusManager.current
    var doneEnabled by rememberSaveable { mutableStateOf(false) }

    val user = viewModel.user.value

    Column(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focus.clearFocus() })
            }
    ) {
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
                    onClick = { /*TODO: Done*/ },
                    enabled = doneEnabled,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Rounded.Done, null)
                }
            }
        )
        when (type) {
            PostType.Common -> PostCreationModule(
                onChange = { text, label, uri ->
                    doneEnabled = text.isNotEmpty() || uri.toString().isNotEmpty()
                }
            )
            PostType.Recipe -> TODO()
        }
    }

    BackHandler { goBack() }
}