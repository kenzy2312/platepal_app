package com.example.recipeapp.data.repository


import com.example.recipeapp.data.remote.RemoteDataSource
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.domain.MealDetail

class MealRepositoryImpl(
    private val remote: RemoteDataSource
) : MealRepository {

    override suspend fun search(query: String): List<Meal> {
        val q = query.trim()
        if (q.isEmpty()) return emptyList()
        return remote.searchMealsByName(q)
    }
    override suspend fun detail(id: String): MealDetail = remote.mealDetail(id)

    override suspend fun getRandomMeal(): Meal {
        return remote.getRandomMeal()
    }

    override suspend fun getAllMeals(): List<Meal> {
        return remote.getAllMeals()
    }
}
