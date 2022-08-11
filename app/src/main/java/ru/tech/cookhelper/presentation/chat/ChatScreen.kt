package ru.tech.cookhelper.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.chat.components.ChatBubbleItem
import ru.tech.cookhelper.presentation.chat.viewModel.ChatViewModel
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = scopedViewModel()) {
    var value by remember { mutableStateOf("") }
    val state = rememberLazyListState()

    LaunchedEffect(viewModel.messages.size) {
        if (viewModel.messages.isNotEmpty()) state.animateScrollToItem(viewModel.messages.size - 1)
    }

    Column(
        Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = state,
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            itemsIndexed(viewModel.messages) { index, message ->
                val cutTopCorner = viewModel.messages.getOrNull(index - 1)?.userId != message.userId
                ChatBubbleItem(
                    user = viewModel.user.value?.copy(id = 1),
                    message = message,
                    cutTopCorner = cutTopCorner
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                shape = RoundedCornerShape(24.dp)
            )
            FilledTonalButton(onClick = {
                viewModel.send(value)
                value = ""
            }, modifier = Modifier.padding(end = 12.dp)) {
                Text("Кокнуть")
            }
        }
    }
}