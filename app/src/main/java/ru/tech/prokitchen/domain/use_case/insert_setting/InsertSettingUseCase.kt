package ru.tech.prokitchen.domain.use_case.insert_setting

import ru.tech.prokitchen.domain.repository.ProKitchenRepository
import javax.inject.Inject

class InsertSettingUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    suspend operator fun invoke(id: Int, option: String) = repository.insertSetting(id, option)

}