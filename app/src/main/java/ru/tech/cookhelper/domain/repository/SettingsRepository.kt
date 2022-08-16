package ru.tech.cookhelper.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.domain.model.Setting

interface SettingsRepository {

    fun getSettings(): Flow<List<Setting>>

    suspend fun insertSetting(id: Int, option: String)

}