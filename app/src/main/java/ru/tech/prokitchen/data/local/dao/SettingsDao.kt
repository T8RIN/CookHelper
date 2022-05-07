package ru.tech.prokitchen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.tech.prokitchen.data.local.entity.SettingsEntity

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settingsentity")
    fun getSettings(): Flow<List<SettingsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: SettingsEntity)

}