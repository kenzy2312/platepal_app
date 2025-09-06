package com.example.recipeapp.Database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey



@Entity(
    tableName = "favorite_meals",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteMealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String?,
    val thumbUrl: String?,
    val userId: Int
)
