package ru.tech.cookhelper.presentation.ui.utils.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelClearer.containsClass
import androidx.lifecycle.ViewModelClearer.name
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.currentScreen

@Composable
inline fun <reified VM : ViewModel> scopedViewModel(
    ignoreDisposing: List<Any> = emptyList()
): VM = rememberScoped(ignoreDisposing = ignoreDisposing) { viewModel() }

@Composable
fun <VM : ViewModel> rememberScoped(
    ignoreDisposing: List<Any>,
    builder: @Composable () -> VM
): VM {
    val scopedViewModelContainer: ScopedViewModelContainer = viewModel()

    val viewModelStore = LocalViewModelStoreOwner.current?.viewModelStore

    val savedStateRegistry = LocalSavedStateRegistryOwner.current.savedStateRegistry

    ObserveLifecycleWithScopedViewModelContainer(scopedViewModelContainer)

    val scopedViewModel: VM = scopedViewModelContainer.getOrBuildViewModel(builder = builder)

    val screenController = LocalScreenController.current
    DisposableEffect(Unit) {
        onDispose {
            screenController.currentScreen.apply {
                if (!ignoreDisposing.containsClass(this)) {
                    scopedViewModelContainer.onDisposedFromComposition(
                        key = scopedViewModel::class.name,
                        viewModelStore = viewModelStore!!,
                        savedStateRegistry = savedStateRegistry
                    )
                }
            }
        }
    }

    return scopedViewModel
}

@Composable
private fun ObserveLifecycleWithScopedViewModelContainer(scopedViewModelContainer: ScopedViewModelContainer) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(lifecycle, scopedViewModelContainer) {
        launch(Dispatchers.Main) {
            lifecycle.addObserver(scopedViewModelContainer)
        }
    }
}