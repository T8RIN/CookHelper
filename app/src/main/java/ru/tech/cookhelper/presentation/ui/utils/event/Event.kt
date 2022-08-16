package ru.tech.cookhelper.presentation.ui.utils.event

import ru.tech.cookhelper.presentation.ui.utils.UIText

sealed class Event {
    class ShowSnackbar(val text: UIText, val action: () -> Unit) : Event()
    class ShowToast(val text: UIText) : Event()
}
