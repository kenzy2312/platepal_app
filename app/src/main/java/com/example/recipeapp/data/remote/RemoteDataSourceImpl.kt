package com.example.recipeapp.data.remote

import com.example.recipeapp.network.MealApiService
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.domain.MealDetail
import com.example.recipeapp.data.domain.toDetail
import com.example.recipeapp.data.domain.toDomain


class RemoteDataSourceImpl(
    private val api: MealApiService
) : RemoteDataSource {

    override suspend fun searchMealsByName(query: String): List<Meal> {
        val dto = api.searchByName(query)
        return dto.meals?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun searchMealsByFirstLetter(letter: Char): List<Meal> {
        val dto = api.searchByFirstLetter(letter.toString())
        return dto.meals?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun mealDetail(id: String): MealDetail {
        val dto = api.getById(id)
        val m = dto.meals?.firstOrNull()
            ?: error("Not found")
        return m.toDetail()
    }
}