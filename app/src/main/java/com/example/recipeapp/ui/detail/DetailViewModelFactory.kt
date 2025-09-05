package com.example.recipeapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.domain.GetMealDetail
import com.example.recipeapp.data.repository.FavoritesRepository
import com.example.recipeapp.ui.detail.RecipeDetailViewModel

class DetailViewModelFactory (
    private val mealDetail: GetMealDetail,
    private val favorites: FavoritesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeDetailViewModel::class.java)) {
            return RecipeDetailViewModel(mealDetail, favorites) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}