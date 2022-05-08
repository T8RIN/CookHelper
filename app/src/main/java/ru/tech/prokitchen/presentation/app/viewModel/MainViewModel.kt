package ru.tech.prokitchen.presentation.app.viewModel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.prokitchen.core.Action
import ru.tech.prokitchen.domain.model.Product
import ru.tech.prokitchen.domain.use_case.get_fridge_list.GetFridgeListUseCase
import ru.tech.prokitchen.domain.use_case.get_prod_list.GetProductsListUseCase
import ru.tech.prokitchen.domain.use_case.get_settings_list.GetSettingsListUseCase
import ru.tech.prokitchen.domain.use_case.insert_setting.InsertSettingUseCase
import ru.tech.prokitchen.domain.use_case.update_fridge.UpdateFridgeUseCase
import ru.tech.prokitchen.presentation.app.components.*
import ru.tech.prokitchen.presentation.ui.utils.Dialog
import ru.tech.prokitchen.presentation.ui.utils.Screen
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsListUseCase: GetProductsListUseCase,
    private val getFridgeListUseCase: GetFridgeListUseCase,
    private val updateFridgeUseCase: UpdateFridgeUseCase,
    getSettingsListUseCase: GetSettingsListUseCase,
    private val insertSettingUseCase: InsertSettingUseCase
) : ViewModel() {

    var searchMode by mutableStateOf(false)

    val currentDialog = mutableStateOf<Dialog>(Dialog.None)
    val currentScreen = mutableStateOf<Screen>(Screen.Home)

    var navDestination by mutableStateOf<Screen>(Screen.Recipes)
    var selectedItem by mutableStateOf(0)

    var title by mutableStateOf(Screen.Recipes.title)
    val searchString = mutableStateOf("")

    @ExperimentalMaterial3Api
    val scrollBehavior by mutableStateOf(TopAppBarDefaults.pinnedScrollBehavior())

    private val default: ArrayList<Product> = arrayListOf()
    private val _productsList = mutableStateOf(ProductsListState())
    val productsList: State<ProductsListState> = _productsList

    val tempList = mutableStateListOf<Int>()

    private val _settingsState: MutableState<SettingsState> = mutableStateOf(SettingsState())
    val settingsState: State<SettingsState> = _settingsState

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
                            cartConnection = setting.option.toBooleanStrictOrNull() ?: false
                        )
                    }
                }
            }
            _settingsState.value = locState
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
}