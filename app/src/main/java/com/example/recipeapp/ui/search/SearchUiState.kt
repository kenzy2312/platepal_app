package com.example.recipeapp.ui.search

import com.example.recipeapp.data.domain.Meal

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val results: List<Meal>) : SearchUiState
    data object Empty : SearchUiState
    data class Error(val message: String) : SearchUiState
}