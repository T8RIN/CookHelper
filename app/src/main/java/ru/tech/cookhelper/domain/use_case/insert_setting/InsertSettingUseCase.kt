package ru.tech.cookhelper.domain.use_case.insert_setting

import ru.tech.cookhelper.domain.repository.SettingsRepository
import javax.inject.Inject

class InsertSettingUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(id: Int, option: String) = repository.insertSetting(id, option)

}