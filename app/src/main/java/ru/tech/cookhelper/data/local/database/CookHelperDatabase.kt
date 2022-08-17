package ru.tech.cookhelper.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.tech.cookhelper.data.local.dao.SettingsDao
import ru.tech.cookhelper.data.local.dao.UserDao
import ru.tech.cookhelper.data.local.entity.SettingsEntity
import ru.tech.cookhelper.data.local.entity.UserEntity

@Database(
    entities = [SettingsEntity::class, UserEntity::class],
    exportSchema = false,
    version = 1
)
abstract class CookHelperDatabase : RoomDatabase() {
    abstract val settingsDao: SettingsDao
    abstract val userDao: UserDao
}