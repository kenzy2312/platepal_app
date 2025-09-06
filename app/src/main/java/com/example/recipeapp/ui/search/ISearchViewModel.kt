package com.example.recipeapp.ui.search

import kotlinx.coroutines.flow.StateFlow

interface ISearchViewModel {
    val uiState: StateFlow<SearchUiState>
    fun onQueryChanged(query: String)
}