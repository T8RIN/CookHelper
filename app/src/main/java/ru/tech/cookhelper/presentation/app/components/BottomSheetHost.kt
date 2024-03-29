package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.animateContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.popAll
import ru.tech.cookhelper.presentation.forum_screen.components.ForumFilterBottomSheet
import ru.tech.cookhelper.presentation.ui.utils.navigation.BottomSheet
import ru.tech.cookhelper.presentation.ui.utils.provider.BottomSheetController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalBottomSheetController
import ru.tech.cookhelper.presentation.ui.utils.provider.currentDestination
import ru.tech.cookhelper.presentation.ui.widgets.bottomsheet.ModalBottomSheetScaffold

@Composable
fun BottomSheetHost(
    bottomSheetController: BottomSheetController = LocalBottomSheetController.current,
    content: @Composable () -> Unit
) {
    val controller = bottomSheetController.controller
    val state = bottomSheetController.state

    ModalBottomSheetScaffold(
        modifier = Modifier.animateContentSize(),
        state = state,
        nestedScrollEnabled = controller.currentDestination?.nestedScrollEnabled == true,
        dismissOnTapOutside = controller.currentDestination?.dismissOnTapOutside == true,
        gesturesEnabled = controller.currentDestination?.gesturesEnabled == true,
        onDismiss = controller.currentDestination?.onDismiss ?: { controller.popAll() },
        sheetContent = {
            NavHost(
                controller = controller,
            ) { sheet ->
                when (sheet) {
                    is BottomSheet.ForumFilter -> {
                        ForumFilterBottomSheet(
                            filters = sheet.filters,
                            onFiltersChange = sheet.onFiltersChange
                        )
                    }
                }
            }
        },
        content = content
    )
}