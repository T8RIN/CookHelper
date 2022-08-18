package ru.tech.cookhelper.presentation.settings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.tech.cookhelper.domain.use_case.insert_setting.InsertSettingUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val insertSettingUseCase: InsertSettingUseCase
) : ViewModel() {
    fun insertSetting(id: Int, option: Any) {
        viewModelScope.launch {
            insertSettingUseCase(id, option.toString())
        }
    }
}