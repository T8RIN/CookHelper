package ru.tech.cookhelper.presentation.fridge_list.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.use_case.get_fridge_list.GetFridgeListUseCase
import ru.tech.cookhelper.presentation.fridge_list.components.FridgeListState
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class FridgeViewModel @Inject constructor(
    private val getFridgeListUseCase: GetFridgeListUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _fridgeListState = mutableStateOf(FridgeListState())
    val fridgeListState: State<FridgeListState> = _fridgeListState

    init {
        getFridgeListUseCase().onEach { result ->
            when (result) {
                is Action.Success -> {
                    _fridgeListState.value =
                        FridgeListState(products = result.data?.sortedBy { it.name })
                }
                is Action.Error -> {
                    _fridgeListState.value = _fridgeListState.value.copy(isLoading = false)
                    sendEvent(Event.ShowToast(UIText.DynamicString(result.message ?: "")))
                }
                is Action.Loading -> {
                    _fridgeListState.value = FridgeListState(isLoading = true)
                }
                is Action.Empty -> TODO()
            }
        }.launchIn(viewModelScope)
    }

}