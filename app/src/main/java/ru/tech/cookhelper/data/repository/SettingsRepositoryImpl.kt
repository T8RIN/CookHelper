package ru.tech.cookhelper.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tech.cookhelper.data.local.dao.SettingsDao
import ru.tech.cookhelper.data.local.entity.SettingsEntity
import ru.tech.cookhelper.data.local.entity.toSetting
import ru.tech.cookhelper.domain.model.Setting
import ru.tech.cookhelper.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao
) : SettingsRepository {

    override fun getSettings(): Flow<List<Setting>> =
        settingsDao.getSettings()
            .map { settingsList ->
                settingsList.map { entity -> entity.toSetting() }
            }

    override suspend fun insertSetting(id: Int, option: String) {
        settingsDao.insertSetting(SettingsEntity(id, option))
    }

}