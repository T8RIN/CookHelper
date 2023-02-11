package ru.tech.cookhelper.presentation.topic_creation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.rounded.*
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
import androidx.core.net.toUri
import com.google.accompanist.flowlayout.FlowRow
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.getLastAvatar
import ru.tech.cookhelper.presentation.forum_discussion.components.TagItem
import ru.tech.cookhelper.presentation.recipe_post_creation.components.LeaveUnsavedDataDialog
import ru.tech.cookhelper.presentation.topic_creation.viewModel.TopicCreationViewModel
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.getFile
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.rememberMutableStateListOf
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Picture
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHostState
import ru.tech.cookhelper.presentation.ui.widgets.CozyTextField
import ru.tech.cookhelper.presentation.ui.widgets.LoadingDialog
import ru.tech.cookhelper.presentation.ui.widgets.TextFieldAppearance
import ru.tech.cookhelper.presentation.ui.widgets.TopAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TopicCreationScreen(
    viewModel: TopicCreationViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val toastHost = LocalToastHostState.current
    val focus = LocalFocusManager.current
    var doneEnabled by rememberSaveable { mutableStateOf(false) }

    var content by rememberSaveable { mutableStateOf("") }
    var label by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf("") }
    val tags = rememberMutableStateListOf<String>()

    val user = viewModel.user

    var showLeaveUnsavedDataDialog by rememberSaveable { mutableStateOf(false) }

    val goBack = {
        if (imageUri.isNotEmpty() || content.isNotEmpty() || label.isNotEmpty() || tags.isNotEmpty()) {
            showLeaveUnsavedDataDialog = true
        } else onBack()
    }

    Column(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focus.clearFocus() })
            }
            .animateContentSize()
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
                    Picture(model = user?.getLastAvatar(), modifier = Modifier.size(40.dp))
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
                        val file = context.getFile(imageUri)
                        val type = context.contentResolver.getType(imageUri.toUri()) ?: ""
                        viewModel.createTopic(
                            content,
                            label,
                            tags.filter { it.isNotEmpty() },
                            listOf(file to type)
                        )
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
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                it?.let { uri ->
                    imageUri = uri.toString()
                }
                if (it == null) {
                    toastHost.show(
                        Icons.Rounded.BrokenImage,
                        context.getString(R.string.image_not_picked)
                    )
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
                            stringResource(R.string.headline),
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
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(
                            stringResource(R.string.question),
                            modifier = Modifier.offset(y = 4.dp)
                        )
                    },
                    textStyle = TextStyle(fontSize = 20.sp)
                )

                var enterTagValue by rememberSaveable { mutableStateOf("") }

                CozyTextField(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
                        .fillMaxWidth(),
                    appearance = TextFieldAppearance.Rounded,
                    value = enterTagValue,
                    onValueChange = {
                        enterTagValue = it
                        if (it.contains("\n")) {
                            if (!tags.contains(enterTagValue.trim())) tags.add(enterTagValue.trim())
                            enterTagValue = ""
                        }
                    },
                    endIcon = {
                        AnimatedVisibility(
                            visible = enterTagValue.isNotEmpty(),
                            enter = fadeIn() + scaleIn(),
                            exit = fadeOut() + scaleOut()
                        ) {
                            IconButton(
                                onClick = {
                                    if (!tags.contains(enterTagValue.trim())) tags.add(enterTagValue.trim())
                                    enterTagValue = ""
                                }
                            ) {
                                Icon(Icons.Rounded.CheckCircleOutline, null)
                            }
                        }
                    },
                    maxLines = 2,
                    label = stringResource(R.string.add_tag)
                )
                val newTags by remember {
                    derivedStateOf {
                        tags.filter { it.isNotEmpty() }
                    }
                }
                AnimatedVisibility(visible = newTags.isNotEmpty()) {
                    Column {
                        Text(
                            text = stringResource(R.string.tags),
                            modifier = Modifier.padding(12.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        AnimatedContent(
                            targetState = newTags,
                            transitionSpec = { fadeIn() with fadeOut() }
                        ) {
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, bottom = 32.dp),
                                mainAxisSpacing = 8.dp,
                                crossAxisSpacing = 8.dp,
                            ) {
                                it.forEach {
                                    TagItem(
                                        text = it,
                                        onClick = {
                                            tags.remove(it)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                AnimatedContent(targetState = imageUri) { uri ->
                    if (uri.isEmpty()) {
                        OutlinedButton(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxWidth(),
                            onClick = {
                                resultLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }
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

        LaunchedEffect(content, label) {
            doneEnabled = content.isNotEmpty() && label.isNotEmpty()
        }

        LoadingDialog(
            visible = viewModel.topicCreationState.isLoading,
            isCancelable = true,
            onDismiss = {

            }
        )

        if (viewModel.topicCreationState.topic != null) {
            SideEffect {
                toastHost.show(
                    Icons.Rounded.Done, UIText(R.string.topic_created).asString(context)
                )
                onBack()
            }

        }

    }

    BackHandler { goBack() }

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.show(
                it.icon,
                it.text.asString(context)
            )
            else -> {}
        }
    }

    if (showLeaveUnsavedDataDialog) {
        LeaveUnsavedDataDialog(
            title = R.string.topic_creation_started,
            message = R.string.topic_creation_started_leave_message,
            onLeave = { onBack() },
            onDismissRequest = { showLeaveUnsavedDataDialog = false }
        )
    }
}