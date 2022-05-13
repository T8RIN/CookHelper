package ru.tech.cookhelper.domain.use_case.insert_setting

import ru.tech.cookhelper.domain.repository.ProKitchenRepository
import javax.inject.Inject

class InsertSettingUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    suspend operator fun invoke(id: Int, option: String) = repository.insertSetting(id, option)

}