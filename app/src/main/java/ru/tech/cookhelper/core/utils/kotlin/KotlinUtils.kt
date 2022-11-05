package ru.tech.cookhelper.core.utils.kotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

inline fun <T> T.applyCatching(
    block: T.() -> Unit
): T = apply {
    runCatching { block() }
}

inline fun <T> Result<T>.getOrExceptionAndNull(action: (Throwable) -> Unit): T? = try {
    getOrThrow()
} catch (t: Throwable) {
    action(t)
    null
}

suspend fun <T> runIo(
    function: suspend () -> T
): T = withContext(Dispatchers.IO) { function() }

fun String.cptlize(): String = replaceFirstChar { it.titlecase() }