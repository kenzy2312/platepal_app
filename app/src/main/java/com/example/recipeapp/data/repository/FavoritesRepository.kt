package com.example.recipeapp.data.repository

import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.domain.MealDetail
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun favoritesFlow(): Flow<List<Meal>>          // UI list
    suspend fun isFavorite(id: String): Boolean

    suspend fun toggle(meal: Meal): Boolean
    suspend fun toggle(detail: MealDetail): Boolean

    suspend fun removeById(id: String)

    suspend fun add(meal: Meal)
    suspend fun add(detail: MealDetail)
}

