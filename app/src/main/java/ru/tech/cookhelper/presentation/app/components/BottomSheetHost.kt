package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import dev.olshevski.navigation.reimagined.NavHost
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.theme.CreateAlt
import ru.tech.cookhelper.presentation.ui.utils.compose.bottomsheet.BottomSheetLayout
import ru.tech.cookhelper.presentation.ui.utils.compose.bottomsheet.BottomSheetValue
import ru.tech.cookhelper.presentation.ui.utils.compose.bottomsheet.rememberBottomSheetState
import ru.tech.cookhelper.presentation.ui.utils.navigation.BottomSheet
import ru.tech.cookhelper.presentation.ui.utils.provider.BottomSheetController
import ru.tech.cookhelper.presentation.ui.utils.provider.close

@Composable
fun BottomSheetHost(controller: BottomSheetController, content: @Composable () -> Unit) {
    val state = rememberBottomSheetState(BottomSheetValue.Collapsed)
    val scope = rememberCoroutineScope()

    BottomSheetLayout(
        state = state,
        onDismiss = { controller.close() },
        sheetContent = {
            NavHost(
                controller = controller,
            ) { sheet ->
                when (sheet) {
                    BottomSheet.ForumFilter -> {
                        Placeholder(
                            icon = Icons.Rounded.CreateAlt,
                            text = "COCK",
                            Modifier.fillMaxSize(0.5f)
                        )
                    }
                }
                LaunchedEffect(controller) {
                    scope.launch { state.expand() }
                }
            }
        },
        content = content
    )
}