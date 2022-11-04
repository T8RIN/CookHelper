package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList

object StateUtils {
    inline fun <T> MutableState<T>.update(
        transform: T.() -> T
    ) = apply { setValue(value = transform(this.value)) }

    inline fun <T> MutableState<T>.updateIf(
        predicate: T.() -> Boolean,
        transform: T.() -> T
    ) = apply { if (predicate(this.value)) transform(this.value) }

    fun <T> MutableState<T>.setValue(value: T) {
        this.value = value
    }

    @Composable
    fun <T : Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> {
        return rememberSaveable(
            saver = listSaver(
                save = { stateList ->
                    if (stateList.isNotEmpty()) {
                        val first = stateList.first()
                        if (!canBeSaved(first)) {
                            throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                        }
                    }
                    stateList.toList()
                },
                restore = { it.toMutableStateList() }
            )
        ) {
            elements.toList().toMutableStateList()
        }
    }
}