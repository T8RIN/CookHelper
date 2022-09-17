package ru.tech.cookhelper.presentation.ui.utils.event

import androidx.compose.ui.graphics.vector.ImageVector
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen

sealed class Event {
    class ShowSnackbar(val text: UIText, val action: () -> Unit) : Event()
    class ShowToast(val text: UIText, val icon: ImageVector? = null) : Event()
    class NavigateTo(val screen: Screen) : Event()
    class NavigateIf(val predicate: (Screen?) -> Boolean, val screen: Screen) : Event()

    @Suppress("UNCHECKED_CAST")
    class SendData(val data: Map<String, Any>) : Event() {
        operator fun <T> get(key: String): T? = data[key] as? T
    }
}
