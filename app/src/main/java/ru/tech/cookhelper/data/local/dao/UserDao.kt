package ru.tech.cookhelper.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity")
    fun getUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheUser(user: UserEntity)

    @Query("DELETE FROM userentity")
    suspend fun clearUser()

}