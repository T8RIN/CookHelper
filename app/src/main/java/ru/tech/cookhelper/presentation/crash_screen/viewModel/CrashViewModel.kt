package ru.tech.cookhelper.presentation.crash_screen.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import ru.tech.cookhelper.domain.use_case.get_settings_list.GetSettingsListUseCase
import ru.tech.cookhelper.domain.use_case.get_settings_list.GetSettingsListUseCaseFlow
import ru.tech.cookhelper.presentation.settings.components.SettingsState
import ru.tech.cookhelper.presentation.settings.components.mapToState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import javax.inject.Inject

@HiltViewModel
class CrashViewModel @Inject constructor(
    getSettingsListUseCase: GetSettingsListUseCase,
    getSettingsListUseCaseFlow: GetSettingsListUseCaseFlow
) : ViewModel() {

    private val _settingsState: MutableState<SettingsState> = mutableStateOf(
        runBlocking { getSettingsListUseCase().mapToState() }
    )
    val settingsState: SettingsState by _settingsState

    init {
        getSettingsListUseCaseFlow().onEach {
            _settingsState.update { it.mapToState() }
        }.launchIn(viewModelScope)
    }

}