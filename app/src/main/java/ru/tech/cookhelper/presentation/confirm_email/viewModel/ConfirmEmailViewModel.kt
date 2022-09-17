package ru.tech.cookhelper.presentation.confirm_email.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.cache_user.CacheUserUseCase
import ru.tech.cookhelper.domain.use_case.check_code.CheckCodeUseCase
import ru.tech.cookhelper.domain.use_case.request_code.RequestCodeUseCase
import ru.tech.cookhelper.presentation.confirm_email.components.CodeState
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEvents
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class ConfirmEmailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val checkCodeUseCase: CheckCodeUseCase,
    private val requestCodeUseCase: RequestCodeUseCase,
    private val cacheUserUseCase: CacheUserUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    val email: String = savedStateHandle["email"] ?: ""
    val name: String = savedStateHandle["name"] ?: ""
    val token: String = savedStateHandle["token"] ?: ""

    private var timerJob: Job? = null

    var codeTimeout by mutableStateOf(60)

    private val _codeState: MutableState<CodeState> = mutableStateOf(CodeState())
    val codeState: CodeState by _codeState

    init {
        reloadTimer()
    }

    fun checkVerificationCode(code: String) {
        checkCodeUseCase(code, token).onEach { result ->
            when (result) {
                is Action.Loading -> _codeState.update { CodeState(isLoading = true) }
                is Action.Error -> {
                    _codeState.update { CodeState(error = true) }
                    sendEvent(
                        Event.ShowToast(
                            UIText.DynamicString(result.message.toString()),
                            Icons.Rounded.ErrorOutline
                        ),
                    )
                }
                is Action.Success -> {
                    result.data?.let {
                        sendEvent(
                            Event.ShowToast(
                                UIText.StringResource(
                                    R.string.welcome_user,
                                    it.name
                                ), Icons.Outlined.Face
                            )
                        )
                        cacheUser(it)
                    }
                }
                is Action.Empty -> {
                    _codeState.update { CodeState(error = true) }
                    sendEvent(
                        Event.ShowToast(
                            UIText.StringResource(R.string.wrong_code),
                            Icons.Rounded.ErrorOutline
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun askForVerificationCode() {
        viewModelScope.launch {
            val result = requestCodeUseCase(token)
            if (result.isFailure) {
                sendEvent(
                    Event.ShowToast(
                        UIText.DynamicString(result.exceptionOrNull()?.message.toString()),
                        Icons.Rounded.ErrorOutline
                    )
                )
            } else reloadTimer()
        }
    }

    private fun cacheUser(user: User) = viewModelScope.launch { cacheUserUseCase(user) }

    private fun reloadTimer() {
        codeTimeout = 60

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (codeTimeout != 0) {
                delay(1000)
                codeTimeout--
            }
            timerJob?.cancel()
        }
    }

}