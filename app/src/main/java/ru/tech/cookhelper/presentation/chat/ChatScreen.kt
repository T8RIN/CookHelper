package ru.tech.cookhelper.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.chat.viewModel.ChatViewModel
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = scopedViewModel()) {
    var value by remember { mutableStateOf("") }
    val state = rememberLazyListState()
    Column(
        Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(Modifier.weight(1f), state = state) {
            items(viewModel.messages) {
                Text(it.text)
                Spacer(Modifier.size(10.dp))
            }
        }
        TextField(value = value, onValueChange = { value = it })
        FilledTonalButton(onClick = { viewModel.send(value) }) {
            Text("Кокнуть")
        }
    }
}