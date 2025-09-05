package com.example.recipeapp.data.repository

import com.example.recipeapp.data.domain.MealDetail
import com.example.recipeapp.db.FavoriteMealEntity
import com.example.recipeapp.db.FavoritesDao

class FavoritesRepositoryImpl(private val dao: FavoritesDao): FavoritesRepository {
    override suspend fun isFavorite(id: String) = dao.get(id) != null

    override suspend fun toggleFavorite(detail: MealDetail): Boolean {
        val existing = dao.get(detail.id)
        return if (existing == null) {
            dao.insert(FavoriteMealEntity(detail.id, detail.name, detail.category, detail.thumbUrl))
            true
        } else {
            dao.delete(existing)
            false
        }
    }
}