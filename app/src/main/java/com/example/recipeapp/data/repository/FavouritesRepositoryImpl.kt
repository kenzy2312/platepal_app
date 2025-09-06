package com.example.recipeapp.data.repository

import com.example.recipeapp.data.domain.MealDetail
import com.example.recipeapp.Database.FavoriteMealEntity
import com.example.recipeapp.Database.FavoritesDao
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.app.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val dao: FavoritesDao ,
    private val sessionManager: SessionManager
) : FavoritesRepository {

    private fun currentUserId(): Int {
        val userId = sessionManager.getUserId()
        if (userId == -1) throw IllegalStateException("No user logged in")
        return userId
    }

    override fun favoritesFlow(): Flow<List<Meal>> =
        dao.observeAll(currentUserId()).map { list ->
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
        dao.exists(id, currentUserId())

    // --- Toggle overloads ---
    override suspend fun toggle(meal: Meal): Boolean {
        val userId = currentUserId()
        return if (dao.exists(meal.id, userId)) {
            dao.deleteById(meal.id, userId)
            false
        } else {
            add(meal)
            true
        }
    }

    override suspend fun toggle(detail: MealDetail): Boolean {
        val userId = currentUserId()
        return if (dao.exists(detail.id, userId)) {
            dao.deleteById(detail.id, userId)
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
                thumbUrl = meal.thumbUrl,
                userId = currentUserId()
            )
        )
    }

    override suspend fun add(detail: MealDetail) {
        dao.insert(
            FavoriteMealEntity(
                id = detail.id,
                name = detail.name,
                category = detail.category,
                thumbUrl = detail.thumbUrl,
                userId = currentUserId()
            )
        )
    }

    override suspend fun removeById(id: String) {
        dao.deleteById(id, currentUserId())
    }
}