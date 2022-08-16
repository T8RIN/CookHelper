package ru.tech.cookhelper.presentation.chat

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.*
import ru.tech.cookhelper.presentation.chat.components.MessageBubbleItem
import ru.tech.cookhelper.presentation.chat.components.MessageHeader
import ru.tech.cookhelper.presentation.chat.viewModel.ChatViewModel
import ru.tech.cookhelper.presentation.ui.utils.ColorUtils.createSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.StateUtils.computedStateOf
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    chatId: String, viewModel: ChatViewModel = hiltViewModel(
        defaultArguments = bundleOf("chatId" to chatId)
    ), onBack: () -> Unit
) {
    val chatState = viewModel.chatState.value
    var value by remember { mutableStateOf("") }
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val textBoxColor = TopAppBarDefaults
        .smallTopAppBarColors()
        .containerColor(
            colorTransitionFraction = 1f
        ).value

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior by remember {
        mutableStateOf(
            TopAppBarDefaults.pinnedScrollBehavior(
                topAppBarState
            )
        )
    }

    val isAtTheBottom by computedStateOf { state.isLastItemVisible }
    var fabBottomPadding by remember { mutableStateOf(0) }
    var fabTopPadding by remember { mutableStateOf(0) }

    val scrollToBottom = {
        if (viewModel.messages.isNotEmpty()) scope.launch {
            state.animateScrollToItem(viewModel.messages.size - 1)
        }
    }
    var newMessages by rememberSaveable { mutableStateOf(0) }

    val user = viewModel.user.value

    LaunchedEffect(isAtTheBottom) {
        if (isAtTheBottom) newMessages = 0
    }

    LaunchedEffect(viewModel.messages.size) {
        if (viewModel.messages.lastOrNull()?.userId == user?.id) scrollToBottom()
        if (!isAtTheBottom) newMessages++
    }

    if (chatState.errorMessage.isNotEmpty()) {
        LocalToastHost.current.sendToast(
            Icons.Rounded.ErrorOutline,
            chatState.errorMessage.asString()
        )
        viewModel.resetState()
    }

    Box {
        Column(
            Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            TopAppBar(
                modifier = Modifier.onSizeChanged {
                    fabTopPadding = it.height
                },
                size = Size.Centered,
                scrollBehavior = scrollBehavior,
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
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Rounded.ArrowBack, null)
                    }
                },
            )
            AnimatedVisibility(
                visible = chatState.isLoading,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            TopAppBarDefaults
                                .smallTopAppBarColors()
                                .containerColor(
                                    colorTransitionFraction = scrollBehavior.state.overlappedFraction
                                ).value
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.weight(1f))
                    Spacer(Modifier.width(16.dp))
                    Text(stringResource(R.string.waiting_for_connection), fontSize = 18.sp)
                    Spacer(Modifier.width(16.dp))
                    Loading(
                        Modifier
                            .wrapContentWidth()
                            .padding(vertical = 8.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Spacer(Modifier.weight(1f))
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
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

                    if (topDay != currentDay) stickyHeader {
                        MessageHeader(
                            text = formatOrNull(
                                message.timestamp,
                                "d MMMM"
                            ).toString()
                        )
                    }

                    item {
                        val topTime = formatOrNull(higherMessage?.timestamp)
                        val currentTime = formatOrNull(message.timestamp)
                        val bottomTime = formatOrNull(lowerMessage?.timestamp)

                        val cutTopCorner =
                            higherMessage?.userId != message.userId || topTime != currentTime
                        val showPointingArrow =
                            lowerMessage?.userId != message.userId || (topTime != currentTime && currentTime != bottomTime) || (topTime == currentTime && bottomTime != currentTime)

                        MessageBubbleItem(
                            isMessageFromCurrentUser = (viewModel.user.value?.id
                                ?: 0) == message.userId,
                            text = message.text,
                            timestamp = message.timestamp,
                            cutTopCorner = cutTopCorner,
                            showPointingArrow = showPointingArrow
                        )
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
                val sendButtonWidth =
                    animateDpAsState(targetValue = if (emptyField) 0.dp else 48.dp)
                val sendButtonAlpha = animateFloatAsState(targetValue = if (emptyField) 0f else 1f)
                val hintAlpha = animateFloatAsState(targetValue = if (emptyField) 1f else 0f)
                val hintOffset =
                    animateDpAsState(targetValue = if (emptyField) 0.dp else LocalConfiguration.current.screenWidthDp.dp / 2f)
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
                        val colors = TextFieldDefaults.outlinedTextFieldColors()
                        CompositionLocalProvider(LocalTextSelectionColors provides colors.selectionColors) {
                            BasicTextField(
                                value = value,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onValueChange = { value = it },
                                textStyle = LocalTextStyle.current.copy(
                                    color = colors.textColor(true).value,
                                    fontSize = 16.sp
                                ),
                                cursorBrush = SolidColor(colors.cursorColor(false).value),
                            )
                        }
                        Text(
                            fontSize = 16.sp,
                            text = stringResource(R.string.write_a_message),
                            color = textBoxColor.createSecondaryColor(0.5f),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .offset(x = hintOffset.value)
                                .alpha(hintAlpha.value),
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
                            .width(sendButtonWidth.value)
                            .alpha(sendButtonAlpha.value),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Rounded.Send, null)
                    }
                }
            }

        }

        AnimatedVisibility(
            visible = !isAtTheBottom && newMessages != 0,
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
                SmallFloatingActionButton(
                    onClick = { scrollToBottom() },
                    modifier = Modifier.padding(8.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(Icons.Rounded.KeyboardArrowDown, null)
                }
                Badge(
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd),
                    containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    Text(newMessages.toString())
                }
            }
        }
    }

    BackHandler { onBack() }
}

private fun formatOrNull(
    timestamp: Long?,
    pattern: String = "HH:mm"
): String? {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return if (timestamp == null) null else formatter.format(timestamp)
}

private val LazyListState.isLastItemVisible: Boolean
    get() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
