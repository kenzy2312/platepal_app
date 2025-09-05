package com.example.recipeapp.ui.search

import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.ui.search.SearchUiState
import kotlinx.coroutines.flow.StateFlow

interface ISearchViewModel {
    val uiState: StateFlow<SearchUiState>
    fun onQueryChanged(query: String)
    fun onResultClicked(meal: Meal) // navigation handled by Fragment for now
}