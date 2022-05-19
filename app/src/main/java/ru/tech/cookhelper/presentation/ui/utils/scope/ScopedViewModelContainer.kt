package ru.tech.cookhelper.presentation.ui.utils.scope

import androidx.compose.runtime.Composable
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelClearer.clearViewModel
import androidx.savedstate.SavedStateRegistry
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.getPrivateProperty
import ru.tech.cookhelper.presentation.ui.utils.getPrivatePropertyName
import ru.tech.cookhelper.presentation.ui.utils.name
import ru.tech.cookhelper.presentation.ui.utils.setAndReturnPrivateProperty
import java.util.concurrent.ConcurrentSkipListSet

class ScopedViewModelContainer : ViewModel(), LifecycleEventObserver {

    private var viewModelStore: ViewModelStore? = null

    private var savedStateRegistry: SavedStateRegistry? = null

    private var isInForeground = true

    private val scopedViewModelsContainer = mutableMapOf<String, ViewModel>()

    private val markedForDisposal = ConcurrentSkipListSet<String>()

    private val disposingJobs = mutableMapOf<String, Job>()

    private val disposeDelayTimeMillis: Long = 1000

    @Composable
    fun <VM : ViewModel> getOrBuildViewModel(
        builder: @Composable () -> VM
    ): VM {
        val key: String
        val viewModel = builder.invoke().apply {
            key = this::class.name
            scopedViewModelsContainer[key] = this
        }
        cancelDisposal(key)
        return viewModel
    }

    fun onDisposedFromComposition(
        key: String,
        viewModelStore: ViewModelStore,
        savedStateRegistry: SavedStateRegistry
    ) {
        this.viewModelStore = viewModelStore
        this.savedStateRegistry = savedStateRegistry
        markedForDisposal.add(key)
        scheduleToDisposeBeforeGoingToBackground(key)
    }

    private fun scheduleToDisposeBeforeGoingToBackground(key: String) = scheduleToDispose(key = key)

    private fun scheduleToDisposeAfterReturningFromBackground() {
        markedForDisposal.forEach { key -> scheduleToDispose(key) }
    }

    private fun alreadyDisposing(key: String): Boolean {
        return disposingJobs.containsKey(key)
    }

    private fun scheduleToDispose(
        key: String,
        removalCondition: () -> Boolean = { isInForeground }
    ) {
        if (alreadyDisposing(key)) return

        val newDisposingJob = viewModelScope.launch {
            delay(disposeDelayTimeMillis)
            if (removalCondition()) {
                markedForDisposal.remove(key)
                scopedViewModelsContainer.remove(key)
                    ?.also {
                        if (shouldClearDisposedViewModel(it)) clearDisposedViewModel(it)
                    }
            }
            disposingJobs.remove(key)
        }
        disposingJobs[key] = newDisposingJob
    }

    private fun shouldClearDisposedViewModel(disposedViewModel: ViewModel): Boolean =
        !scopedViewModelsContainer.containsValue(disposedViewModel)

    @Suppress("UNCHECKED_CAST")
    private fun clearDisposedViewModel(scopedViewModel: ViewModel) {
        val name = scopedViewModel.javaClass.name

        val mapName = viewModelStore.getPrivatePropertyName(HashMap<String, ViewModel>()::class)[0]

        val mMap = viewModelStore.getPrivateProperty(mapName) as HashMap<String, ViewModel>
        val key = "$TAG:$name"
        mMap[key]?.clearViewModel()
        mMap.remove(key)
        viewModelStore.setAndReturnPrivateProperty(mapName, mMap)
        savedStateRegistry?.unregisterSavedStateProvider(name)
    }

    private fun cancelDisposal(key: String) {
        disposingJobs.remove(key)?.cancel()
        markedForDisposal.remove(key)
    }

    override fun onCleared() {
        disposingJobs.forEach { (_, job) -> job.cancel() }
        scopedViewModelsContainer.values.forEach { clearDisposedViewModel(it) }
        scopedViewModelsContainer.clear()
        super.onCleared()
    }

    override fun onStateChanged(lifecycleOwner: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                isInForeground = true
                scheduleToDisposeAfterReturningFromBackground()
            }
            Lifecycle.Event.ON_PAUSE -> {
                isInForeground = false
            }
            Lifecycle.Event.ON_DESTROY -> {
                lifecycleOwner.lifecycle.removeObserver(this)
            }
            else -> {}
        }
    }

    companion object {
        private const val TAG = "androidx.lifecycle.ViewModelProvider.DefaultKey"
    }
}