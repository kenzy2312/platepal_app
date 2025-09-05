package com.example.recipeapp.data.domain

import com.example.recipeapp.data.repository.MealRepository

interface GetMealDetail { suspend operator fun invoke(id: String): MealDetail }
class GetMealDetailImpl(private val repo: MealRepository) : GetMealDetail {
    override suspend fun invoke(id: String) = repo.detail(id)
}