package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.compositionLocalOf
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.popAll
import ru.tech.cookhelper.presentation.ui.utils.navigation.BottomSheet
import ru.tech.cookhelper.presentation.ui.widgets.bottomsheet.BottomSheetState

val LocalBottomSheetController =
    compositionLocalOf<BottomSheetController> { error("BottomSheetController not present") }

suspend fun BottomSheetController.show(sheet: BottomSheet) {
    controller.navigateAndPopAll(sheet)
    state.expand()
}

suspend fun BottomSheetController.close() {
    state.collapse()
    controller.popAll()
}

data class BottomSheetController(
    val controller: NavController<BottomSheet>,
    val state: BottomSheetState
)