package ru.tech.cookhelper.domain.use_case.get_settings_list

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.domain.model.Setting
import ru.tech.cookhelper.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsListUseCaseFlow @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<List<Setting>> = repository.getSettingsFlow()
}

class GetSettingsListUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): List<Setting> = repository.getSettings()
}