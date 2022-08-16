package ru.tech.cookhelper.core.utils

import kotlin.reflect.KClass

object ReflectionUtils {
    inline val <T : Any> KClass<T>.name: String
        get() {
            return simpleName.toString()
        }
}