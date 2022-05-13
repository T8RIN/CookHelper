package ru.tech.cookhelper.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.local.entity.ProductEntity

@Dao
interface FridgeDao {

    @Query("SELECT * FROM productentity")
    fun getFridgeList(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("DELETE FROM productentity WHERE id = :id")
    suspend fun removeProduct(id: Int)


}