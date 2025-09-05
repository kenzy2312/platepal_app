package com.example.recipeapp.data.repository

import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.domain.MealDetail

interface MealRepository {
    suspend fun search(query: String): List<Meal>
    suspend fun detail(id: String): MealDetail

}