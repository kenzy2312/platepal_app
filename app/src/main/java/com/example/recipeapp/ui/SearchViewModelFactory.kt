package com.example.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.domain.SearchMeals

class SearchViewModelFactory(
    private val searchMeals: SearchMeals
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchMeals) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}