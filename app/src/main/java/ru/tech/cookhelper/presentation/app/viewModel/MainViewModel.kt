package ru.tech.cookhelper.presentation.app.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.olshevski.navigation.reimagined.navController
import dev.olshevski.navigation.reimagined.navigate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.cookhelper.domain.use_case.get_settings_list.GetSettingsListUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.insert_setting.InsertSettingUseCase
import ru.tech.cookhelper.domain.use_case.log_out.LogoutUseCase
import ru.tech.cookhelper.presentation.app.components.*
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.currentDestination
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSettingsListUseCase: GetSettingsListUseCase,
    getUserUseCase: GetUserUseCase,
    private val insertSettingUseCase: InsertSettingUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val screenController = navController<Screen>(
        startDestination = Screen.Home.None
    )

    var title by mutableStateOf(Screen.Home.Recipes.title)

    private val _settingsState: MutableState<SettingsState> = mutableStateOf(SettingsState())
    val settingsState: State<SettingsState> = _settingsState

    private val _userState: MutableState<UserState> = mutableStateOf(UserState())
    val userState: State<UserState> = _userState

    init {
        getSettingsListUseCase().onEach { list ->
            var locState = SettingsState()
            list.forEach { setting ->
                when (setting.id) {
                    Settings.DYNAMIC_COLORS.ordinal -> {
                        locState = locState.copy(
                            dynamicColors = setting.option.toBooleanStrictOrNull() ?: false
                        )
                    }
                    Settings.COLOR_SCHEME.ordinal -> {
                        val index = setting.option.toIntOrNull() ?: ColorScheme.BLUE.ordinal
                        locState = locState.copy(colorScheme = enumValues<ColorScheme>()[index])
                    }
                    Settings.NIGHT_MODE.ordinal -> {
                        val index = setting.option.toIntOrNull() ?: NightMode.SYSTEM.ordinal
                        locState = locState.copy(nightMode = enumValues<NightMode>()[index])
                    }
                    Settings.CART_CONNECTION.ordinal -> {
                        locState = locState.copy(
                            cartConnection = setting.option.toBoolean()
                        )
                    }
                }
            }
            _settingsState.value = locState
        }.launchIn(viewModelScope)

        getUserUseCase().onEach {
            if (it == null) screenController.navigate(Screen.Authentication)
            else {
                if (screenController.currentDestination == Screen.Authentication) screenController.navigate(
                    Screen.Home.Recipes
                )
                _userState.value = UserState(it, it.token)
            }
        }.launchIn(viewModelScope)
    }

    fun insertSetting(id: Int, option: String) {
        viewModelScope.launch {
            insertSettingUseCase(id, option)
        }
    }

    fun logOut() {
        viewModelScope.launch { logoutUseCase() }
    }
}

fun String.toBoolean(): Boolean = this.toBooleanStrictOrNull() ?: false