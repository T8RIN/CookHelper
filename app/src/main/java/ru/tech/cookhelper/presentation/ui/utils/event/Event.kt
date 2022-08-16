package ru.tech.cookhelper.presentation.ui.utils.event

import androidx.compose.ui.graphics.vector.ImageVector
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.UIText

sealed class Event {
    class ShowSnackbar(val text: UIText, val action: () -> Unit) : Event()
    class ShowToast(val text: UIText, val icon: ImageVector? = null) : Event()
    class NavigateTo(val screen: Screen) : Event()
    class NavigateIf(val predicate: (Screen) -> Boolean, val screen: Screen) : Event()
}
