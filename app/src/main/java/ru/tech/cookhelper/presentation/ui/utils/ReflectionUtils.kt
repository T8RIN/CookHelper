package ru.tech.cookhelper.presentation.ui.utils

import kotlin.reflect.KClass

val <T : Any> KClass<T>.name: String
    get() {
        return simpleName.toString()
    }