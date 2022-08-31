package ru.tech.cookhelper.presentation.profile.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.ui.theme.DialogShape
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.asString
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost
import ru.tech.cookhelper.presentation.ui.utils.provider.close

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun PickOrOpenAvatarDialog(
    hasAvatar: Boolean,
    onOpenAvatar: () -> Unit,
    onAvatarPicked: (imageUri: String) -> Unit
) {
    val dialogController = LocalDialogController.current
    val toastHost = LocalToastHost.current
    val context = LocalContext.current

    var imageUri by rememberSaveable { mutableStateOf("") }
    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri == null) {
                toastHost.sendToast(
                    icon = Icons.Outlined.BrokenImage,
                    message = (R.string.image_not_picked).asString(context)
                )
                return@rememberLauncherForActivityResult
            }
            imageUri = uri.toString()
        }
    )

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .width(TextFieldDefaults.MinWidth + 40.dp)
            .padding(vertical = 16.dp),
        title = { Text(stringResource(R.string.avatar)) },
        text = {
            AnimatedContent(targetState = imageUri.isEmpty()) { showButtons ->
                if (showButtons) {
                    Column(Modifier.width(TextFieldDefaults.MinWidth)) {
                        if (hasAvatar) {
                            Button(
                                onClick = {
                                    dialogController.close()
                                    onOpenAvatar()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(stringResource(R.string.show_avatar))
                            }
                            Spacer(Modifier.height(4.dp))
                        }
                        Button(
                            onClick = { resultLauncher.launch("image/*") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = if (!hasAvatar) ButtonDefaults.buttonColors() else ButtonDefaults.filledTonalButtonColors()
                        ) {
                            Text(stringResource(R.string.edit_avatar))
                        }
                    }
                } else {
                    Box(Modifier.size(TextFieldDefaults.MinWidth)) {
                        Picture(
                            model = imageUri,
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(24.dp)
                        )
                        FilledIconButton(
                            modifier = Modifier
                                .padding(end = 6.dp, top = 6.dp)
                                .size(40.dp)
                                .align(Alignment.TopEnd),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                            ),
                            onClick = { imageUri = "" }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Cancel,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        },
        shape = DialogShape,
        onDismissRequest = { if (imageUri.isEmpty()) dialogController.close() },
        icon = { Icon(Icons.Outlined.AccountCircle, null) },
        confirmButton = {
            TextButton(onClick = {
                if (imageUri.isNotEmpty()) onAvatarPicked(imageUri)
                dialogController.close()
            }) {
                if (imageUri.isEmpty()) Text(stringResource(R.string.close))
                else Text(stringResource(R.string.add))
            }
        }
    )
}