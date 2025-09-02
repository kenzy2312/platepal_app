package com.example.recipeapp.data.repository


import com.example.recipeapp.data.remote.RemoteDataSource

import com.example.recipeapp.data.domain.Meal

class MealRepositoryImpl(
    private val remote: RemoteDataSource
) : MealRepository {

    override suspend fun search(query: String): List<Meal> {
        val q = query.trim()
        if (q.isEmpty()) return emptyList()
        // For substring matching everywhere, TheMealDB's `s=` is sufficient and returns meals containing q
        // Keep `f=` for a future "starts-with" toggle.
        return remote.searchMealsByName(q)
    }
}
