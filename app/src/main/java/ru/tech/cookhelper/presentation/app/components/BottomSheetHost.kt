package ru.tech.cookhelper.presentation.app.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import dev.olshevski.navigation.reimagined.NavHost
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.forum_screen.components.ForumFilterBottomSheet
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
                        ForumFilterBottomSheet()
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