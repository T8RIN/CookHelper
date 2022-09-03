package ru.tech.cookhelper.presentation.ui.utils

inline fun <T, R> T.provide(block: T.(T) -> R): R {
    return block(this)
}