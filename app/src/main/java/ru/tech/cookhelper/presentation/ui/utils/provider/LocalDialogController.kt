package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.compositionLocalOf
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.navController
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.pop
import ru.tech.cookhelper.presentation.ui.utils.Dialog

val LocalDialogController = compositionLocalOf { navController<Dialog>(Dialog.None) }

fun NavController<Dialog>.show(dialog: Dialog) = navigate(dialog)

fun NavController<Dialog>.close() = pop()

val NavController<Dialog>.currentDialog: Dialog
    get() = currentDestination ?: Dialog.None