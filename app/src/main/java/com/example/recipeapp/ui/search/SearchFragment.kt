package com.example.recipeapp.ui.search

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.app.RecipeApp
import kotlinx.coroutines.launch
import com.example.recipeapp.ui.MealAdapter

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var etSearch: EditText
    private lateinit var rvResults: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var empty: TextView

    private val viewModel: SearchViewModel by viewModels {
        (requireActivity().application as RecipeApp)
            .container.provideSearchViewModelFactory()
    }

    private lateinit var adapter: MealAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etSearch = view.findViewById(R.id.etSearch)
        rvResults = view.findViewById(R.id.rvResults)
        progress = view.findViewById(R.id.progress)
        empty = view.findViewById(R.id.empty)

        adapter = MealAdapter(
            meals = mutableListOf(),
            onItemClick = { meal ->
                val action = SearchFragmentDirections
                    .actionSearchFragmentToRecipeDetailFragment(meal.id)
                requireView().findNavController().navigate(action)
            },
            onFavoriteClick = { meal ->
                viewModel.toggleFavorite(meal)
            }
        )
        rvResults.layoutManager = LinearLayoutManager(requireContext())
        rvResults.adapter = adapter

        etSearch.addTextChangedListener { editable ->
            viewModel.onQueryChanged(editable?.toString().orEmpty())
        }

        observeUi()
    }

    private fun observeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is SearchUiState.Idle -> {
                            progress.visibility = View.GONE
                            empty.visibility = View.GONE
                            adapter.updateMeals(emptyList())
                        }
                        is SearchUiState.Loading -> {
                            progress.visibility = View.VISIBLE
                            empty.visibility = View.GONE
                        }
                        is SearchUiState.Success -> {
                            progress.visibility = View.GONE
                            empty.visibility = View.GONE
                            adapter.updateMeals(state.results)
                        }
                        is SearchUiState.Empty -> {
                            progress.visibility = View.GONE
                            adapter.updateMeals(emptyList())
                            empty.visibility = View.VISIBLE
                        }
                        is SearchUiState.Error -> {
                            progress.visibility = View.GONE
                            empty.text = state.message
                            empty.visibility = View.VISIBLE
                            adapter.updateMeals(emptyList())
                        }
                    }
                }
            }
        }
    }
}