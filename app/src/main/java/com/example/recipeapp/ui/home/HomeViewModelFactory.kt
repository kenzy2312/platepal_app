package com.example.recipeapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.repository.FavoritesRepository
import com.example.recipeapp.data.repository.MealRepository

class HomeViewModelFactory(
    private val mealRepo: MealRepository,
    private val favoritesRepo: FavoritesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mealRepo, favoritesRepo) as T
        }
        throw IllegalArgumentException("Unknown VM: ${modelClass.name}")
    }
}