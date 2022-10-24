package ru.tech.cookhelper.presentation.post_creation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.Post
import ru.tech.cookhelper.presentation.app.components.*
import ru.tech.cookhelper.presentation.post_creation.viewModel.PostCreationViewModel
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.getFile
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost
import ru.tech.cookhelper.presentation.ui.utils.provider.show


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun PostCreationScreen(
    viewModel: PostCreationViewModel = hiltViewModel(),
    initialImageUri: String = "",
    onBack: () -> Unit,
    /*TODO: Remove this shit*/ todoRemoveThisFuckingCostyl: (Post?) -> Unit
) {
    val context = LocalContext.current
    val toastHost = LocalToastHost.current
    val focus = LocalFocusManager.current
    var doneEnabled by rememberSaveable { mutableStateOf(false) }

    var content by rememberSaveable { mutableStateOf("") }
    var label by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf(initialImageUri) }

    val user = viewModel.user

    val dialogController = LocalDialogController.current

    val goBack = {
        if (imageUri.isNotEmpty() || content.isNotEmpty() || label.isNotEmpty()) {
            dialogController.show(
                Dialog.LeaveUnsavedData(
                    title = R.string.post_creation_started,
                    message = R.string.post_creation_started_leave_message,
                    onLeave = { onBack() }
                )
            )
        } else onBack()
    }

    Column(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focus.clearFocus() })
            }
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
                    Picture(model = user?.avatar?.firstOrNull(), modifier = Modifier.size(40.dp))
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
                        viewModel.createPost(content, label, context.getFile(imageUri))
                    },
                    enabled = doneEnabled,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Rounded.Done, null)
                }
            }
        )

        val paddingValues = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp)

        val resultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = {
                it?.let { uri ->
                    imageUri = uri.toString()
                }
            }
        )

        LazyColumn(
            Modifier
                .fillMaxSize()
                .imePadding(),
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        ) {
            item {
                CozyTextField(
                    value = label,
                    appearance = TextFieldAppearance.Rounded,
                    onValueChange = { label = it },
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(
                            stringResource(R.string.enter_headline),
                            modifier = Modifier.offset(y = 4.dp)
                        )
                    },
                    textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                CozyTextField(
                    value = content,
                    appearance = TextFieldAppearance.Rounded,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 32.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(
                            stringResource(R.string.whats_new),
                            modifier = Modifier.offset(y = 4.dp)
                        )
                    },
                    textStyle = TextStyle(fontSize = 20.sp)
                )

                AnimatedContent(targetState = imageUri) { uri ->
                    if (uri.isEmpty()) {
                        OutlinedButton(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxWidth(),
                            onClick = { resultLauncher.launch("image/*") }
                        ) {
                            Spacer(Modifier.width(8.dp))
                            Icon(Icons.Outlined.Image, null)
                            Spacer(Modifier.width(16.dp))
                            Text(stringResource(R.string.add_image))
                            Spacer(Modifier.width(8.dp))
                        }
                    } else {
                        Box {
                            Picture(
                                model = imageUri,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(paddingValues),
                                shape = RoundedCornerShape(24.dp)
                            )
                            FilledIconButton(
                                modifier = Modifier
                                    .padding(end = 14.dp, top = 6.dp)
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
            }
        }

        LaunchedEffect(imageUri, content) {
            doneEnabled = imageUri.isNotEmpty() || content.isNotEmpty()
        }

        LoadingDialog(visible = viewModel.postCreationState.isLoading)

        if (viewModel.postCreationState.post != null) {
            SideEffect {
                toastHost.sendToast(
                    Icons.Rounded.Done, UIText(R.string.post_created).asString(context)
                )
                onBack()
                todoRemoveThisFuckingCostyl(viewModel.postCreationState.post)
            }

        }

    }

    BackHandler { goBack() }

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.sendToast(
                it.icon,
                it.text.asString(context)
            )
            else -> {}
        }
    }
}