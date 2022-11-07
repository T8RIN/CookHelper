package ru.tech.cookhelper.presentation.fridge_screen.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DoneOutline
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.onEmpty
import ru.tech.cookhelper.core.onError
import ru.tech.cookhelper.core.onSuccess
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.use_case.add_products_to_fridge.AddProductsToFridgeUseCase
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.domain.use_case.remove_products_from_fridge.RemoveProductsFromFridgeUseCase
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
    private val addProductsToFridgeUseCase: AddProductsToFridgeUseCase,
    private val removeProductsFromFridgeUseCase: RemoveProductsFromFridgeUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _user: MutableState<UserState> = mutableStateOf(UserState())
    val userState: UserState by _user

    init {
        getUserUseCase().onEach {
            _user.update { UserState(it) }
        }.launchIn(viewModelScope)
    }

    fun addProductsToFridge(products: List<Product>) {
        viewModelScope.launch {
            addProductsToFridgeUseCase(userState.token, products)
                .onSuccess {
                    sendEvent(
                        Event.ShowToast(
                            UIText(R.string.added_to_fridge),
                            Icons.Rounded.DoneOutline
                        )
                    )
                }
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

    fun removeProductsFromFridge(products: List<Product>) {
        viewModelScope.launch {
            removeProductsFromFridgeUseCase(userState.token, products)
                .onSuccess {
                    sendEvent(
                        Event.ShowToast(
                            UIText(R.string.removed_from_fridge),
                            Icons.Rounded.DoneOutline
                        )
                    )
                }
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


}