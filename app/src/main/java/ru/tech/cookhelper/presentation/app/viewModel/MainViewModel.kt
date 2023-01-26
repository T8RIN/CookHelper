package ru.tech.cookhelper.presentation.app.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.onSuccess
import ru.tech.cookhelper.domain.use_case.cache_user.CacheUserUseCase
import ru.tech.cookhelper.domain.use_case.get_settings_list.GetSettingsListUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.observe_user.ObserveUserUseCase
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.settings.components.NightMode
import ru.tech.cookhelper.presentation.settings.components.Setting
import ru.tech.cookhelper.presentation.settings.components.SettingsState
import ru.tech.cookhelper.presentation.ui.theme.ColorScheme
import ru.tech.cookhelper.presentation.ui.theme.colorList
import ru.tech.cookhelper.presentation.ui.theme.ordinal
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSettingsListUseCase: GetSettingsListUseCase,
    getUserUseCase: GetUserUseCase,
    cacheUserUseCase: CacheUserUseCase,
    observeUserUseCase: ObserveUserUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _title: MutableState<UIText> = mutableStateOf(Screen.Home.Feed.title)
    val title: UIText by _title

    private val _settingsState: MutableState<SettingsState> = mutableStateOf(SettingsState())
    val settingsState: SettingsState by _settingsState

    private val _userState: MutableState<UserState> = mutableStateOf(UserState())
    val userState: UserState by _userState

    private var observingJob: Job? = null

    init {
        getSettingsListUseCase().onEach { list ->
            var state = SettingsState()
            list.forEach { setting ->
                when (setting.id) {
                    Setting.DYNAMIC_COLORS.ordinal -> {
                        state = state.copy(dynamicColors = setting.option.toBoolean())
                    }
                    Setting.COLOR_SCHEME.ordinal -> {
                        val index = setting.option.toIntOrNull() ?: ColorScheme.Blue.ordinal
                        state = state.copy(colorScheme = colorList[index])
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
                    Setting.CUSTOM_ACCENT.ordinal -> {
                        state = state.copy(
                            customAccent = setting.option.toLongOrNull()?.let { Color(it) }
                        )
                    }
                }
            }
            _settingsState.update { state }
        }.launchIn(viewModelScope)

        getUserUseCase().onEach { user ->
            if (user == null) {
                observingJob?.cancel()
                observingJob = null
                sendEvent(Event.NavigateTo(Screen.Authentication.Login))
            } else {
                _userState.update { UserState(user, user.token) }
                if (observingJob == null) {
                    observingJob = observeUserUseCase(userState.user?.id ?: 0, userState.token)
                        .onSuccess {
                            cacheUserUseCase(this)
                        }
                        .launchIn(viewModelScope)
                }
            }
        }.launchIn(viewModelScope)

    }

    fun updateTitle(newTitle: UIText) {
        _title.update { newTitle }
    }
}

fun String.toBoolean(): Boolean = this.toBooleanStrictOrNull() ?: false