package com.example.recipeapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.domain.SearchMeals
import com.example.recipeapp.data.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMeals: SearchMeals,
    private val favoritesRepo: FavoritesRepository
) : ViewModel(), ISearchViewModel {

    private val _query = MutableStateFlow("")
    private val _ui = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    override val uiState: StateFlow<SearchUiState> = _ui.asStateFlow()


    init {
        viewModelScope.launch {
            _query
                .debounce(300)               // user types, wait 300ms
                .distinctUntilChanged()
                .collectLatest { q ->
                    if (q.isBlank()) {
                        _ui.value = SearchUiState.Idle
                        return@collectLatest
                    }
                    _ui.value = SearchUiState.Loading
                    try {
                        val results = searchMeals(q)
                        _ui.value = if (results.isEmpty()) SearchUiState.Empty
                        else SearchUiState.Success(results)
                    } catch (t: Throwable) {
                        _ui.value = SearchUiState.Error(t.message ?: "Unexpected error")
                    }
                }
        }
    }

    override fun onQueryChanged(query: String) {
        _query.value = query
    }
    fun toggleFavorite(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            val newIsFav = favoritesRepo.toggle(meal)
            meal.isFavorite = newIsFav
        }
    }
}