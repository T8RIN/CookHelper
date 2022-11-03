package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.compositionLocalOf
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.pop
import ru.tech.cookhelper.presentation.ui.utils.navigation.BottomSheet

val LocalBottomSheetController =
    compositionLocalOf<NavController<BottomSheet>> { error("BottomSheetController not present") }

fun BottomSheetController.show(sheet: BottomSheet) = navigate(sheet)

fun BottomSheetController.close() = pop()

typealias BottomSheetController = NavController<BottomSheet>