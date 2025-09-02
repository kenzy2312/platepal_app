package com.example.recipeapp.data.repository

import com.example.recipeapp.data.domain.Meal

interface MealRepository {
    suspend fun search(query: String): List<Meal>
}