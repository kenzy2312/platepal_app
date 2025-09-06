package com.example.recipeapp.ui.Favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.repository.FavoritesRepository

class FavoritesViewModelFactory(
    private val favoritesRepo: FavoritesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(favoritesRepo) as T
        }
        throw IllegalArgumentException("Unknown VM: ${modelClass.name}")
    }
}