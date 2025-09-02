package com.example.recipeapp.ui

import com.example.recipeapp.data.domain.Meal
import kotlinx.coroutines.flow.StateFlow

interface ISearchViewModel {
    val uiState: StateFlow<SearchUiState>
    fun onQueryChanged(query: String)
    fun onResultClicked(meal: Meal) // navigation handled by Fragment for now
}