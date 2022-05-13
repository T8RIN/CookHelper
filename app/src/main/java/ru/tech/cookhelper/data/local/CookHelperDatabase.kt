package ru.tech.cookhelper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.tech.cookhelper.data.local.dao.FavRecipeDao
import ru.tech.cookhelper.data.local.dao.FridgeDao
import ru.tech.cookhelper.data.local.dao.SettingsDao
import ru.tech.cookhelper.data.local.entity.FavRecipeEntity
import ru.tech.cookhelper.data.local.entity.ProductEntity
import ru.tech.cookhelper.data.local.entity.SettingsEntity

@Database(
    entities = [FavRecipeEntity::class, ProductEntity::class, SettingsEntity::class],
    exportSchema = false,
    version = 1
)
abstract class CookHelperDatabase : RoomDatabase() {
    abstract val favRecipeDao: FavRecipeDao
    abstract val fridgeDao: FridgeDao
    abstract val settingsDao: SettingsDao
}