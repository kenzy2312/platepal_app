package com.example.recipeapp.data.repository

import com.example.recipeapp.data.domain.MealDetail
import com.example.recipeapp.Database.FavoriteMealEntity
import com.example.recipeapp.Database.FavoritesDao
import com.example.recipeapp.data.domain.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val dao: FavoritesDao
) : FavoritesRepository {

    override fun favoritesFlow(): Flow<List<Meal>> =
        dao.observeAll().map { list ->
            list.map { e ->
                Meal(
                    id = e.id,
                    name = e.name,
                    category = e.category,
                    thumbUrl = e.thumbUrl
                )
            }
        }

    override suspend fun isFavorite(id: String): Boolean =
        dao.exists(id)

    // --- Toggle overloads ---

    override suspend fun toggle(meal: Meal): Boolean {
        return if (dao.exists(meal.id)) {
            dao.deleteById(meal.id)
            false
        } else {
            add(meal)
            true
        }
    }

    override suspend fun toggle(detail: MealDetail): Boolean {
        return if (dao.exists(detail.id)) {
            dao.deleteById(detail.id)
            false
        } else {
            add(detail)
            true
        }
    }

    // --- Add overloads ---

    override suspend fun add(meal: Meal) {
        dao.insert(
            FavoriteMealEntity(
                id = meal.id,
                name = meal.name,
                category = meal.category,
                thumbUrl = meal.thumbUrl
            )
        )
    }

    override suspend fun add(detail: MealDetail) {
        dao.insert(
            FavoriteMealEntity(
                id = detail.id,
                name = detail.name,
                category = detail.category,
                thumbUrl = detail.thumbUrl
            )
        )
    }

    override suspend fun removeById(id: String) {
        dao.deleteById(id)
    }
}