package com.example.recipeapp.data

import com.example.recipeapp.data.domain.Meal

object FavoritesManager {
    private val favorites = mutableListOf<Meal>()

    fun addMeal(meal: Meal) {
        favorites.add(meal)
    }

    fun removeMeal(meal: Meal) {
        favorites.remove(meal)
    }

    fun getFavorites(): List<Meal> = favorites
}
