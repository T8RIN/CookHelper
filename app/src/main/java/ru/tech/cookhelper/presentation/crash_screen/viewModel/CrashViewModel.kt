package ru.tech.cookhelper.presentation.crash_screen.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.domain.use_case.get_settings_list.GetSettingsListUseCase
import ru.tech.cookhelper.presentation.app.viewModel.toBoolean
import ru.tech.cookhelper.presentation.settings.components.NightMode
import ru.tech.cookhelper.presentation.settings.components.Setting
import ru.tech.cookhelper.presentation.settings.components.SettingsState
import ru.tech.cookhelper.presentation.ui.theme.colorList
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import javax.inject.Inject

@HiltViewModel
class CrashViewModel @Inject constructor(
    getSettingsListUseCase: GetSettingsListUseCase
) : ViewModel() {

    private val _settingsState: MutableState<SettingsState> = mutableStateOf(SettingsState())
    val settingsState: SettingsState by _settingsState

    init {
        getSettingsListUseCase().onEach { list ->
            var state = SettingsState()
            list.forEach { setting ->
                when (setting.id) {
                    Setting.DYNAMIC_COLORS.ordinal -> {
                        state = state.copy(dynamicColors = setting.option.toBoolean())
                    }
                    Setting.CUSTOM_ACCENT.ordinal -> {
                        state = state.copy(
                            customAccent = setting.option.toLongOrNull()?.let { Color(it) }
                                ?: Color.Blue
                        )
                    }
                    Setting.COLOR_SCHEME.ordinal -> {
                        val index = setting.option.toIntOrNull() ?: -1
                        state = state.copy(colorScheme = colorList.getOrNull(index))
                    }
                    Setting.NIGHT_MODE.ordinal -> {
                        val index = setting.option.toIntOrNull() ?: NightMode.SYSTEM.ordinal
                        state = state.copy(nightMode = enumValues<NightMode>()[index])
                    }
                    Setting.CART_CONNECTION.ordinal -> {
                        state = state.copy(
                            cartConnection = setting.option.toBoolean()
                        )
                    }
                    Setting.LANGUAGE.ordinal -> {
                        state = state.copy(
                            language = setting.option
                        )
                    }
                    Setting.FONT_SCALE.ordinal -> {
                        state = state.copy(
                            fontScale = setting.option.toFloatOrNull() ?: 1f
                        )
                    }
                    Setting.PURE_BLACK.ordinal -> {
                        state = state.copy(
                            pureBlack = setting.option.toBooleanStrictOrNull() ?: false
                        )
                    }
                    Setting.KEEP_SCREEN_ON.ordinal -> {
                        state = state.copy(
                            keepScreenOn = setting.option.toBooleanStrictOrNull() ?: false
                        )
                    }
                }
            }
            _settingsState.update { state }
        }.launchIn(viewModelScope)
    }

}