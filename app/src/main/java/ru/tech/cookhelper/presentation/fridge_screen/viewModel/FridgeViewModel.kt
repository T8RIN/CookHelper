package ru.tech.cookhelper.presentation.fridge_screen.viewModel

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
import ru.tech.cookhelper.domain.use_case.add_products_to_fridge.AddProductsToFridgeUseCase
import ru.tech.cookhelper.domain.use_case.get_available_products.GetAvailableProductsUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import ru.tech.cookhelper.presentation.ui.utils.getUIText
import javax.inject.Inject

@HiltViewModel
class FridgeViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    getAvailableProductsUseCase: GetAvailableProductsUseCase,
    private val addProductsToFridgeUseCase: AddProductsToFridgeUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _user: MutableState<UserState> = mutableStateOf(UserState())
    val userState: UserState by _user

    private val _allProducts: SnapshotStateList<Product> = mutableStateListOf()
    val allProducts: List<Product> = _allProducts

    init {
        getUserUseCase().onEach {
            _user.update { UserState(it) }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
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
                            UIText(this),
                            Icons.Rounded.ErrorOutline
                        )
                    )
                }
        }
    }

    fun addProductsToFridge(products: List<Product>) {
        viewModelScope.launch {
            addProductsToFridgeUseCase(userState.token, products)
                .onEmpty {
                    sendEvent(
                        Event.ShowToast(
                            getUIText(),
                            Icons.Rounded.ErrorOutline
                        )
                    )
                }.onError {
                    sendEvent(
                        Event.ShowToast(
                            UIText(this),
                            Icons.Rounded.ErrorOutline
                        )
                    )
                }
        }
    }

    fun deleteProduct(product: Product) {
        TODO("Not yet implemented")
    }


}