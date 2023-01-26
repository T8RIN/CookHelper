package ru.tech.cookhelper.presentation.ui.utils.provider

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.pop
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog

val LocalDialogController = compositionLocalOf<NavController<Dialog>> {
    error("DialogController not present")
}

fun DialogController.show(dialog: Dialog) = navigateAndPopAll(dialog)

fun DialogController.close() = pop()

typealias DialogController = NavController<Dialog>

@Composable
fun Activity?.dialogBackHandler(onBack: () -> Unit = {}) {
    val dialogController = LocalDialogController.current
    BackHandler {
        dialogController.show(Dialog.Exit(onExit = { this?.finishAffinity() }))
        onBack()
    }
}