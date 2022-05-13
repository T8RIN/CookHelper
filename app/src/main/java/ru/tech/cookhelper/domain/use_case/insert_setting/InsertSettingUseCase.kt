package ru.tech.cookhelper.domain.use_case.insert_setting

import ru.tech.cookhelper.domain.repository.CookHelperRepository
import javax.inject.Inject

class InsertSettingUseCase @Inject constructor(
    private val repository: CookHelperRepository
) {

    suspend operator fun invoke(id: Int, option: String) = repository.insertSetting(id, option)

}