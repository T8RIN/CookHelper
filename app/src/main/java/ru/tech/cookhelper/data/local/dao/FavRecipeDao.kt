package ru.tech.cookhelper.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.local.entity.FavRecipeEntity

@Dao
interface FavRecipeDao {

    @Query("SELECT * FROM favrecipeentity")
    fun getFavRecipes(): Flow<List<FavRecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(favRecipeEntity: FavRecipeEntity)

    @Query("DELETE FROM favrecipeentity WHERE id = :id")
    suspend fun deleteRecipe(id: Int)

    @Query("SELECT * FROM favrecipeentity WHERE id = :id")
    suspend fun getById(id: Int): FavRecipeEntity?

}