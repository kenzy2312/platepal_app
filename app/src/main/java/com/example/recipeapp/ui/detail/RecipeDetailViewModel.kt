package com.example.recipeapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.domain.GetMealDetail
import com.example.recipeapp.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val getMealDetail: GetMealDetail,
    private val favorites: FavoritesRepository
) : ViewModel() {

    private val _ui = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val ui: StateFlow<DetailUiState> = _ui.asStateFlow()

    fun load(id: String) = viewModelScope.launch {
        try {
            val d = getMealDetail(id)
            val fav = favorites.isFavorite(id)
            _ui.value = DetailUiState.Loaded(d, fav)
        } catch (t: Throwable) {
            _ui.value = DetailUiState.Error(t.message ?: "Failed to load")
        }
    }

    fun toggleFavorite() = viewModelScope.launch {
        val current = _ui.value as? DetailUiState.Loaded ?: return@launch
        val newFav = favorites.toggleFavorite(current.detail)
        _ui.value = current.copy(isFavorite = newFav)
    }
}