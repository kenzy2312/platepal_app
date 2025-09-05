package com.example.recipeapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class FavoriteMealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String?,
    val thumbUrl: String?
)