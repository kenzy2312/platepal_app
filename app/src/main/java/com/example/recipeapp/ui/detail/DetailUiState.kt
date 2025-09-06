package com.example.recipeapp.ui.detail

import com.example.recipeapp.data.domain.MealDetail

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Loaded(val detail: MealDetail, val isFavorite: Boolean) : DetailUiState
    data class Error(val message: String) : DetailUiState
}