package com.example.recipeapp.data.remote

import com.example.recipeapp.data.domain.Meal


interface RemoteDataSource {
    suspend fun searchMealsByName(query: String): List<Meal>
    suspend fun searchMealsByFirstLetter(letter: Char): List<Meal>
}

