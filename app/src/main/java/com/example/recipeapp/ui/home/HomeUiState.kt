package com.example.recipeapp.ui.home

import com.example.recipeapp.data.domain.Meal

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Loaded(
        val suggested: Meal?,
        val list: List<Meal>
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}