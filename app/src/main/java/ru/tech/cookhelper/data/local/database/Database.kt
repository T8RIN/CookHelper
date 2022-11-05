package ru.tech.cookhelper.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.tech.cookhelper.data.local.dao.SettingsDao
import ru.tech.cookhelper.data.local.dao.UserDao
import ru.tech.cookhelper.data.local.entity.SettingsEntity
import ru.tech.cookhelper.data.local.entity.UserEntity
import androidx.room.TypeConverters as RoomTypeConverters

@Database(
    entities = [SettingsEntity::class, UserEntity::class],
    exportSchema = false, version = 1,
    // autoMigrations = [AutoMigration(from = 1, to = 2)]
)
@RoomTypeConverters(TypeConverters::class)
abstract class Database : RoomDatabase() {
    abstract val settingsDao: SettingsDao
    abstract val userDao: UserDao
}