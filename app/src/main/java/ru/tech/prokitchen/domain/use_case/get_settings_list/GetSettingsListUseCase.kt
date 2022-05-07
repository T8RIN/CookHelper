package ru.tech.prokitchen.domain.use_case.get_settings_list

import kotlinx.coroutines.flow.Flow
import ru.tech.prokitchen.domain.model.Setting
import ru.tech.prokitchen.domain.repository.ProKitchenRepository
import javax.inject.Inject

class GetSettingsListUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    operator fun invoke(): Flow<List<Setting>> = repository.getSettings()

}