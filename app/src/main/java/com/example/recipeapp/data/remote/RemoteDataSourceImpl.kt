package com.example.recipeapp.data.remote

import com.example.recipeapp.network.MealApiService
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.domain.toDomain

class RemoteDataSourceImpl(
    private val api: MealApiService
) : RemoteDataSource {

    override suspend fun searchMealsByName(query: String): List<Meal> {
        val dto = api.searchByName(query) // returns MealsResponseDto
        return dto.meals?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun searchMealsByFirstLetter(letter: Char): List<Meal> {
        val dto = api.searchByFirstLetter(letter.toString())
        return dto.meals?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getRandomMeal(): Meal {
        val dto = api.getRandomMeal()
        val mealDto = dto.meals?.firstOrNull()
            ?: throw Exception("No random meal found")
        return mealDto.toDomain()
    }

    override suspend fun getAllMeals(): List<Meal> {
        val dto = api.getAllMeals() // make sure this endpoint exists in MealApiService
        return dto.meals?.map { it.toDomain() } ?: emptyList()
    }
}
