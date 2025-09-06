package com.example.recipeapp.data.remote

import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.domain.MealDetail


interface RemoteDataSource {
    suspend fun searchMealsByName(query: String): List<Meal>
    suspend fun searchMealsByFirstLetter(letter: Char): List<Meal>
    suspend fun mealDetail(id: String): MealDetail
}

    // For HomeFragment
    suspend fun getRandomMeal(): Meal
    suspend fun getAllMeals(): List<Meal>
}
