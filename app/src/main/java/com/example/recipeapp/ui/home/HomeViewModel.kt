package com.example.recipeapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.repository.FavoritesRepository
import com.example.recipeapp.data.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mealRepo: MealRepository,
    private val favoritesRepo: FavoritesRepository
) : ViewModel() {

    private val _ui = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val ui: StateFlow<HomeUiState> = _ui.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _ui.value = HomeUiState.Loading
            try {
                val suggested = mealRepo.getRandomMeal()
                val all = mealRepo.getAllMeals()

                // hydrate favorites
                val suggestedFav = suggested.copy(
                    isFavorite = favoritesRepo.isFavorite(suggested.id)
                )
                val hydrated = all.map { m ->
                    m.copy(isFavorite = favoritesRepo.isFavorite(m.id))
                }

                _ui.value = HomeUiState.Loaded(suggestedFav, hydrated)
            } catch (t: Throwable) {
                _ui.value = HomeUiState.Error(t.message ?: "Failed to load")
            }
        }
    }

    fun toggleFavorite(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            // repo returns the *new* state (true = added)
            val nowFav = favoritesRepo.toggle(meal)
            // optimistic update in UI state
            val current = _ui.value as? HomeUiState.Loaded ?: return@launch
            val newSuggested = current.suggested?.let {
                if (it.id == meal.id) it.copy(isFavorite = nowFav) else it
            }
            val newList = current.list.map { m ->
                if (m.id == meal.id) m.copy(isFavorite = nowFav) else m
            }
            _ui.value = HomeUiState.Loaded(newSuggested, newList)
        }
    }
}