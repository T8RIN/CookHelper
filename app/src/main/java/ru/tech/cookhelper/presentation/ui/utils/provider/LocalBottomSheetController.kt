package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.compositionLocalOf
import dev.olshevski.navigation.reimagined.NavController
import ru.tech.cookhelper.presentation.ui.utils.compose.bottomsheet.BottomSheetState
import ru.tech.cookhelper.presentation.ui.utils.navigation.BottomSheet

val LocalBottomSheetController =
    compositionLocalOf<BottomSheetController> { error("BottomSheetController not present") }

suspend fun BottomSheetController.show(sheet: BottomSheet) {
    controller.apply {
        navigateAndPopAll(sheet)
        state.expand()
    }
}

suspend fun BottomSheetController.close() = state.collapse()

data class BottomSheetController(
    val controller: NavController<BottomSheet>,
    val state: BottomSheetState
)