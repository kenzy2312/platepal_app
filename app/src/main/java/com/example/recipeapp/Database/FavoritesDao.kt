package com.example.recipeapp.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorite_meals WHERE id = :id")
    suspend fun get(id: String): FavoriteMealEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(entity: FavoriteMealEntity)

    @Delete
    suspend fun delete(entity: FavoriteMealEntity)

    @Query("SELECT * FROM favorite_meals")
    suspend fun all(): List<FavoriteMealEntity>
}