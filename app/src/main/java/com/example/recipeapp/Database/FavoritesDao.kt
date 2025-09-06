package com.example.recipeapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorite_meals WHERE userId = :userId ORDER BY name COLLATE NOCASE")
    fun observeAll(userId: Int): Flow<List<FavoriteMealEntity>>

    @Query("SELECT * FROM favorite_meals WHERE id = :id AND userId = :userId")
    suspend fun get(id: String, userId: Int): FavoriteMealEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteMealEntity)
    @Query("DELETE FROM favorite_meals WHERE id = :id AND userId = :userId")
    suspend fun deleteById(id: String, userId: Int)
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE id = :id AND userId = :userId)")
    suspend fun exists(id: String, userId: Int): Boolean

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId")
    suspend fun all(userId: Int): List<FavoriteMealEntity>
}
