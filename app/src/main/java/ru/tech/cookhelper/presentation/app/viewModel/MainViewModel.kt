package ru.tech.cookhelper.presentation.app.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.use_case.get_fridge_list.GetFridgeListUseCase
import ru.tech.cookhelper.domain.use_case.get_prod_list.GetProductsListUseCase
import ru.tech.cookhelper.domain.use_case.get_settings_list.GetSettingsListUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.insert_setting.InsertSettingUseCase
import ru.tech.cookhelper.domain.use_case.log_out.LogoutUseCase
import ru.tech.cookhelper.domain.use_case.update_fridge.UpdateFridgeUseCase
import ru.tech.cookhelper.presentation.app.components.*
import ru.tech.cookhelper.presentation.ui.utils.Dialog
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.UIText
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsListUseCase: GetProductsListUseCase,
    private val getFridgeListUseCase: GetFridgeListUseCase,
    private val updateFridgeUseCase: UpdateFridgeUseCase,
    getSettingsListUseCase: GetSettingsListUseCase,
    getUserUseCase: GetUserUseCase,
    private val insertSettingUseCase: InsertSettingUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    var searchMode by mutableStateOf(false)

    val currentDialog = mutableStateOf<Dialog>(Dialog.None)
    val currentScreen = mutableStateOf<Screen>(Screen.Home)

    var navDestination by mutableStateOf<Screen>(Screen.Recipes)
    var selectedItem by mutableStateOf(0)

    var title by mutableStateOf<UIText>(UIText.StringResource(Screen.Recipes.title))
    val searchString = mutableStateOf("")

    private val default: ArrayList<Product> = arrayListOf()
    private val _productsList = mutableStateOf(ProductsListState())
    val productsList: State<ProductsListState> = _productsList

    val tempList = mutableStateListOf<Int>()

    private val _settingsState: MutableState<SettingsState> = mutableStateOf(SettingsState())
    val settingsState: State<SettingsState> = _settingsState

    private val _userState: MutableState<UserState> = mutableStateOf(UserState())
    val userState: State<UserState> = _userState

    init {
        fetchList()
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
            if (it == null) currentScreen.value = Screen.Authentication
            else {
                if (currentScreen.value == Screen.Authentication) currentScreen.value = Screen.Home
                _userState.value = UserState(it, it.token)
            }
        }.launchIn(viewModelScope)
    }

    fun reload() {
        fetchList()
    }

    private fun fetchList() {
        getProductsListUseCase().onEach { result ->
            when (result) {
                is Action.Success -> {
                    _productsList.value =
                        ProductsListState(list = result.data?.sortedBy { it.name })

                    default.clear()
                    result.data?.let { default.addAll(it) }
                }
                is Action.Error -> {
                    _productsList.value = ProductsListState(
                        error = result.message.toString()
                    )
                }
                is Action.Loading -> {
                    _productsList.value = ProductsListState(isLoading = true)
                }
                is Action.Empty -> {}
            }
        }.launchIn(viewModelScope)
        getFridgeListUseCase().onEach { result ->
            if (result is Action.Success && default.isNotEmpty() && result.data != null) {
                _productsList.value =
                    ProductsListState(list = (default - result.data).sortedBy { it.name })
            }
        }.launchIn(viewModelScope)
    }

    fun processToFridge() {
        viewModelScope.launch {
            tempList.forEach {
                updateFridgeUseCase(it, false)
            }
            tempList.clear()
        }
    }

    fun updateSearch(newSearch: String) {
        searchString.value = newSearch
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