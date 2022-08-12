package ru.tech.cookhelper.presentation.chat

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.*
import ru.tech.cookhelper.presentation.chat.components.ChatBubbleItem
import ru.tech.cookhelper.presentation.chat.viewModel.ChatViewModel
import ru.tech.cookhelper.presentation.ui.utils.ColorUtils.createSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = scopedViewModel(), chatId: String, onBack: () -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.awaitAndGetMessages(chatId)
    }

    val chatState = viewModel.chatState.value
    var value by remember { mutableStateOf("") }
    val state = rememberLazyListState()

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

    val user = viewModel.user.value


    LaunchedEffect(viewModel.messages.size) {
        if (viewModel.messages.isNotEmpty()) state.animateScrollToItem(viewModel.messages.size - 1)
    }

    if (chatState.errorMessage.isNotEmpty()) {
        LocalToastHost.current.sendToast(
            Icons.Rounded.ErrorOutline,
            chatState.errorMessage.asString()
        )
        viewModel.resetState()
    }

    Column(
        Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        TopAppBar(
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
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            itemsIndexed(viewModel.messages) { index, message ->
                val cutTopCorner = viewModel.messages.getOrNull(index - 1)?.userId != message.userId
                val showPointingArrow =
                    viewModel.messages.getOrNull(index + 1)?.userId != message.userId
                ChatBubbleItem(
                    user = viewModel.user.value?.copy(id = 1),
                    message = message,
                    cutTopCorner = cutTopCorner,
                    showPointingArrow = showPointingArrow
                )
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
            val sendButtonWidth =
                animateDpAsState(targetValue = if (value.isEmpty()) 0.dp else 48.dp)
            val sendButtonAlpha = animateFloatAsState(targetValue = if (value.isEmpty()) 0f else 1f)
            val hintAlpha = animateFloatAsState(targetValue = if (value.isEmpty()) 1f else 0f)
            Row(
                Modifier.navigationBarsPadding(),
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
                            modifier = Modifier.fillMaxWidth(),
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

    BackHandler { onBack() }
}