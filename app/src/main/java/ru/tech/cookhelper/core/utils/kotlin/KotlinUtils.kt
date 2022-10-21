package ru.tech.cookhelper.core.utils.kotlin

inline fun <T> T.applyCatching(
    block: T.() -> Unit
): T = apply {
    runCatching { block() }
}