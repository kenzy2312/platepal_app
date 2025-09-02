package com.example.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.domain.SearchMeals
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMeals: SearchMeals
) : ViewModel(), ISearchViewModel {

    private val _query = MutableStateFlow("")
    private val _ui = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    override val uiState: StateFlow<SearchUiState> = _ui.asStateFlow()

    private var currentSearchJob: Job? = null

    init {
        viewModelScope.launch {
            _query
                .debounce(300)               // user types; wait 300ms of silence
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

    override fun onResultClicked(meal: Meal) {
        // No-op here; Fragment decides navigation target later.
    }
}