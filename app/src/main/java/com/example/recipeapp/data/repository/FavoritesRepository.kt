package com.example.recipeapp.data.repository

import com.example.recipeapp.data.domain.MealDetail

interface FavoritesRepository {
    suspend fun isFavorite(id: String): Boolean
    suspend fun toggleFavorite(detail: MealDetail): Boolean // returns new state
}

