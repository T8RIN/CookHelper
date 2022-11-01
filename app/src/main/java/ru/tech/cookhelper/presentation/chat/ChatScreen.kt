package ru.tech.cookhelper.presentation.chat

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Loading
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.chat.components.MessageBubbleItem
import ru.tech.cookhelper.presentation.chat.components.MessageHeader
import ru.tech.cookhelper.presentation.chat.viewModel.ChatViewModel
import ru.tech.cookhelper.presentation.chat_list.components.ChatPicture
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.FabSize
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.createSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.compose.ScrollUtils.isLastItemVisible
import ru.tech.cookhelper.presentation.ui.utils.compose.TopAppBarUtils.topAppBarScrollBehavior
import ru.tech.cookhelper.presentation.ui.utils.compose.navigationBarsLandscapePadding
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost
import java.text.SimpleDateFormat
import java.util.*

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun ChatScreen(
    title: String,
    image: String?,
    chatId: Long,
    viewModel: ChatViewModel = hiltViewModel(
        defaultArguments = bundleOf(
            "chatId" to chatId,
            "title" to title,
            "image" to image
        )
    ),
    onBack: () -> Unit
) {
    val chatState = viewModel.chatState
    var value by remember { mutableStateOf("") }
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val textBoxColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)

    val scrollBehavior = topAppBarScrollBehavior()

    val isAtTheBottom = state.isLastItemVisible()
    var fabBottomPadding by remember { mutableStateOf(0) }
    var fabTopPadding by remember { mutableStateOf(0) }

    val scrollToBottom = {
        if (viewModel.messages.isNotEmpty()) scope.launch {
            state.animateScrollToItem(viewModel.messages.size - 1)
        }
    }
    val user = viewModel.user

    LaunchedEffect(viewModel.messages.size) {
        if (viewModel.messages.lastOrNull()?.author?.id == user?.id) scrollToBottom()
    }

    Box(Modifier.navigationBarsLandscapePadding()) {
        Column(
            Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            TopAppBar(
                modifier = Modifier.onSizeChanged {
                    fabTopPadding = it.height
                },
                scrollBehavior = scrollBehavior,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth()
                    ) {
                        ChatPicture(
                            modifier = Modifier
                                .size(40.dp),
                            image = viewModel.chatState.image,
                            title = viewModel.chatState.title
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(
                                text = viewModel.chatState.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (chatState.isLoading) {
                                Text(
                                    text = stringResource(R.string.waiting_for_connection),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.outlineVariant
                                )
                            }
                        }
                        if (chatState.isLoading) {
                            Spacer(Modifier.width(10.dp))
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 1.dp
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Rounded.ArrowBack, null)
                    }
                },
            )
            AnimatedContent(
                targetState = viewModel.loadingAllMessages,
                modifier = Modifier.weight(1f),
                transitionSpec = { fadeIn() with fadeOut() }
            ) { loading ->
                if (loading) Loading()
                else {
                    AnimatedContent(
                        targetState = viewModel.messages.isNotEmpty(),
                        modifier = Modifier.weight(1f),
                        transitionSpec = { fadeIn() with fadeOut() }
                    ) { notEmpty ->
                        if (notEmpty) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                                state = state,
                                verticalArrangement = Arrangement.Bottom,
                                contentPadding = PaddingValues(vertical = 12.dp)
                            ) {
                                viewModel.messages.forEachIndexed { index, message ->
                                    val higherMessage = viewModel.messages.getOrNull(index - 1)
                                    val lowerMessage = viewModel.messages.getOrNull(index + 1)

                                    val topDay = formatOrNull(higherMessage?.timestamp, "d")
                                    val currentDay = formatOrNull(message.timestamp, "d")

                                    if (topDay != currentDay) stickyHeader(key = message.timestamp) {
                                        MessageHeader(
                                            text = formatOrNull(
                                                message.timestamp,
                                                "d MMMM"
                                            ).toString()
                                        )
                                    }

                                    item(key = message.id) {
                                        val topTime = formatOrNull(higherMessage?.timestamp)
                                        val currentTime = formatOrNull(message.timestamp)
                                        val bottomTime = formatOrNull(lowerMessage?.timestamp)

                                        val cutTopCorner =
                                            higherMessage?.author?.id != message.author.id || topTime != currentTime
                                        val showPointingArrow =
                                            lowerMessage?.author?.id != message.author.id || (topTime != currentTime && currentTime != bottomTime) || (topTime == currentTime && bottomTime != currentTime)

                                        MessageBubbleItem(
                                            isMessageFromCurrentUser = (viewModel.user?.id
                                                ?: 0) == message.author.id,
                                            text = message.text,
                                            timestamp = message.timestamp,
                                            cutTopCorner = cutTopCorner,
                                            showPointingArrow = showPointingArrow
                                        )
                                    }
                                }
                            }
                        } else {
                            Placeholder(
                                icon = Icons.Rounded.SpeakerNotesOff,
                                text = stringResource(R.string.no_messages)
                            )
                        }
                    }
                }
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = TextFieldDefaults.MinHeight * 0.6f,
                        max = TextFieldDefaults.MinHeight * 3
                    )
                    .background(textBoxColor)
                    .animateContentSize()
            ) {
                val emptyField = value.isEmpty()
                val sendButtonWidth by animateDpAsState(targetValue = if (emptyField) 0.dp else 48.dp)
                val sendButtonAlpha by animateFloatAsState(targetValue = if (emptyField) 0f else 1f)
                val hintAlpha by animateFloatAsState(targetValue = if (emptyField) 1f else 0f)
                val hintOffset by animateDpAsState(targetValue = if (emptyField) 0.dp else LocalConfiguration.current.screenWidthDp.dp / 2f)
                Row(
                    Modifier
                        .navigationBarsPadding()
                        .onSizeChanged {
                            fabBottomPadding = it.height
                        },
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 12.dp, top = 12.dp, bottom = 12.dp, start = 20.dp)
                    ) {
                        BasicTextField(
                            value = value,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onValueChange = { value = it },
                            textStyle = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 16.sp
                            ),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        )
                        Text(
                            fontSize = 16.sp,
                            text = stringResource(R.string.write_a_message),
                            color = textBoxColor.createSecondaryColor(0.5f),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .offset(x = hintOffset)
                                .alpha(hintAlpha),
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.send(value)
                            value = ""
                        },
                        enabled = !chatState.isLoading,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .width(sendButtonWidth)
                            .alpha(sendButtonAlpha),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Rounded.Send, null)
                    }
                }
            }

        }

        AnimatedVisibility(
            visible = !isAtTheBottom && viewModel.chatState.newMessages != 0,
            modifier = Modifier
                .padding(8.dp)
                .padding(
                    bottom = with(LocalDensity.current) { fabBottomPadding.toDp() },
                    top = with(LocalDensity.current) { fabTopPadding.toDp() }
                )
                .align(Alignment.BottomEnd)
                .imePadding()
        ) {
            Box {
                ExpandableFloatingActionButton(
                    onClick = { scrollToBottom() },
                    size = FabSize.Small,
                    modifier = Modifier.padding(8.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    icon = { Icon(Icons.Rounded.KeyboardArrowDown, null) }
                )
                Badge(
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd),
                    containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    Text(viewModel.chatState.newMessages.toString())
                }
            }
        }
    }

    val toastHost = LocalToastHost.current
    val context = LocalContext.current
    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.sendToast(
                Icons.Rounded.ErrorOutline,
                it.text.asString(context)
            )
            else -> {}
        }
    }


    BackHandler { onBack() }
}

fun formatOrNull(
    timestamp: Long?,
    pattern: String = "HH:mm"
): String? {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return if (timestamp == null) null else formatter.format(timestamp)
}
