package androidx.lifecycle

import kotlin.reflect.KClass

/* this package used to access VM.clear() package private method */
internal object ViewModelClearer {
    fun ViewModel.clearViewModel() = clear()

    fun <T : Any?> T.getPrivateProperty(variableName: String): Any? {
        if (this == null) return null

        return javaClass.getDeclaredField(variableName).let { field ->
            field.isAccessible = true
            return@let field.get(this)
        }
    }

    fun <T : Any?> T.setAndReturnPrivateProperty(variableName: String, data: Any): Any? {
        if (this == null) return null

        return javaClass.getDeclaredField(variableName).let { field ->
            field.isAccessible = true
            field.set(this, data)
            return@let field.get(this)
        }
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

}