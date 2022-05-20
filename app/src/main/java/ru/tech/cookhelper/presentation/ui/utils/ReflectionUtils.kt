package ru.tech.cookhelper.presentation.ui.utils

import kotlin.reflect.KClass

inline fun <reified T : Any?> T.getPrivateProperty(variableName: String): Any? {
    if (this == null) return null

    return javaClass.getDeclaredField(variableName).let { field ->
        field.isAccessible = true
        return@let field.get(this)
    }
}

inline fun <reified T : Any?> T.setAndReturnPrivateProperty(variableName: String, data: Any): Any? {
    if (this == null) return null

    return javaClass.getDeclaredField(variableName).let { field ->
        field.isAccessible = true
        field.set(this, data)
        return@let field.get(this)
    }
}

fun <T : Any?, E : Any> T.getPrivatePropertyName(clazz: KClass<E>): Array<out String> {
    if (this == null) return emptyArray()

    val names = arrayListOf<String>()
    javaClass.declaredFields.forEach {
        if (it.type.kotlin == clazz) names.add(it.name)
    }
    return names.toTypedArray()
}

fun <E : Any> List<E>.containsClass(e: E): Boolean {
    forEach {
        if (it::class == e::class) return true
        if (it is KClass<*> && it == e::class) return true
        if (e is KClass<*> && it::class == e) return true
    }
    return false
}

val <T : Any> KClass<T>.name: String
    get() {
        return simpleName.toString()
    }