package androidx.lifecycle

/* this package used to access VM.clear() package private method */
internal object ViewModelClearer {
    fun ViewModel.clearViewModel() = clear()
}