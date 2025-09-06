package com.example.recipeapp.ui.Favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.*

class FavoritesViewModel(
    private val favoritesRepo: FavoritesRepository
) : ViewModel() {

    // Expose the flow as StateFlow for easy UI collection
    val favorites: StateFlow<List<Meal>> =
        favoritesRepo.favoritesFlow()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun remove(meal: Meal) = viewModelScope.launch {
        favoritesRepo.removeById(meal.id)
    }
}