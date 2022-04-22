package ru.tech.prokitchen.presentation.ui.utils.provider

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import ru.tech.prokitchen.presentation.ui.utils.Dialog

val LocalDialogController = compositionLocalOf { mutableStateOf<Dialog>(Dialog.None) }

fun MutableState<Dialog>.show(dialog: Dialog) {
    this.value = dialog
}

fun MutableState<Dialog>.close() {
    this.value = Dialog.None
}


val MutableState<Dialog>.currentDialog: Dialog get() = this.value