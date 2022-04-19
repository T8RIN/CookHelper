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
import ru.tech.prokitchen.domain.use_case.update_fridge.UpdateFridgeUseCase
import ru.tech.prokitchen.presentation.app.components.ProductsListState
import ru.tech.prokitchen.presentation.ui.utils.Screen
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsListUseCase: GetProductsListUseCase,
    private val getFridgeListUseCase: GetFridgeListUseCase,
    private val updateFridgeUseCase: UpdateFridgeUseCase
) : ViewModel() {

    var searchMode by mutableStateOf(false)
    var openSheet by mutableStateOf(false)

    var selectedItem by mutableStateOf(0)
    var title by mutableStateOf(Screen.Cuisine.title)
    val id = mutableStateOf(-1)
    val searchString = mutableStateOf("")

    var showProductsDialog by mutableStateOf(false)

    @ExperimentalMaterial3Api
    val scrollBehavior by mutableStateOf(TopAppBarDefaults.pinnedScrollBehavior())

    private val default: ArrayList<Product> = arrayListOf()
    private val _productsList = mutableStateOf(ProductsListState())
    val productsList: State<ProductsListState> = _productsList

    val tempList = mutableStateListOf<Int>()

    init {
        fetchList()
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
                is Action.Empty -> {
                    _productsList.value = ProductsListState(
                        error = result.message ?: "Нәрсәдер начар булып чыккан"
                    )
                }
                is Action.Loading -> {
                    _productsList.value = ProductsListState(isLoading = true)
                }
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
}