package com.example.recipeapp.data.domain

import com.example.recipeapp.data.repository.MealRepository

interface SearchMeals {
    suspend operator fun invoke(query: String): List<Meal>
}

class SearchMealsImpl(
    private val repository: MealRepository
) : SearchMeals {
    override suspend fun invoke(query: String): List<Meal> = repository.search(query)
}