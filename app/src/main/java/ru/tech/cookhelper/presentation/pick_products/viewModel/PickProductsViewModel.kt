package ru.tech.cookhelper.presentation.pick_products.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.cookhelper.core.onEmpty
import ru.tech.cookhelper.core.onError
import ru.tech.cookhelper.core.onSuccess
import ru.tech.cookhelper.core.utils.kotlin.cptlize
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.use_case.get_available_products.GetAvailableProductsUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.getUIText
import javax.inject.Inject

@HiltViewModel
class PickProductsViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    getAvailableProductsUseCase: GetAvailableProductsUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _selectedProducts: SnapshotStateList<Product> = mutableStateListOf()
    val selectedProducts: List<Product> = _selectedProducts

    private val _allProducts: SnapshotStateList<Product> = mutableStateListOf()
    val allProducts: List<Product> = _allProducts

    private val _user: MutableState<UserState> = mutableStateOf(UserState())
    val userState: UserState by _user

    private val _loadingProducts: MutableState<Boolean> = mutableStateOf(false)
    val loadingProducts: Boolean by _loadingProducts

    init {
        getUserUseCase().onEach {
            _user.update { UserState(it) }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            _loadingProducts.update { true }
            getAvailableProductsUseCase()
                .onSuccess {
                    _allProducts.clear()
                    _allProducts.addAll(this.map { it.copy(title = it.title.cptlize()) })
                }.onEmpty {
                    sendEvent(
                        Event.ShowToast(
                            getUIText(),
                            Icons.Rounded.ErrorOutline
                        )
                    )
                }.onError {
                    sendEvent(
                        Event.ShowToast(
                            UIText.UIText(this),
                            Icons.Rounded.ErrorOutline
                        )
                    )
                }.also { _loadingProducts.update { false } }
        }
    }

    fun addProductToSelection(product: Product) {
        _selectedProducts.add(product)
    }

    fun removeProductFromSelection(product: Product) {
        _selectedProducts.remove(product)
    }

}