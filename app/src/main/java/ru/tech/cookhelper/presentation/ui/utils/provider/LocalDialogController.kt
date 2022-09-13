package ru.tech.cookhelper.presentation.ui.utils.provider

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.navController
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.pop
import ru.tech.cookhelper.core.utils.ReflectionUtils.name
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog

val LocalDialogController = compositionLocalOf { navController<Dialog>(Dialog.None) }

fun DialogController.show(dialog: Dialog) {
    if (currentDialog::class.name != dialog::class.name) navigate(dialog)
}

fun DialogController.close() = pop()

inline val DialogController.currentDialog: Dialog
    get() = currentDestination ?: Dialog.None

typealias DialogController = NavController<Dialog>

@Composable
fun Activity?.dialogBackHandler(onBack: () -> Unit = {}) {
    val dialogController = LocalDialogController.current
    BackHandler {
        dialogController.show(Dialog.Exit(onExit = { this?.finishAffinity() }))
        onBack()
    }
}