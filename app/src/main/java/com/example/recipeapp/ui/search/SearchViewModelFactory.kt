package com.example.recipeapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.domain.SearchMeals
import com.example.recipeapp.data.repository.FavoritesRepository

class SearchViewModelFactory(
    private val searchMeals: SearchMeals,
    private val favoritesRepo: FavoritesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchMeals,favoritesRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}