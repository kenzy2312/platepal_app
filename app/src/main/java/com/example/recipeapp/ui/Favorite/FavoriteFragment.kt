package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.recipeapp.ui.Favorite.FavoritesViewModel
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(R.layout.fragment_favorite) {

    private val viewModel: FavoritesViewModel by viewModels {
        (requireActivity().application as RecipeApp)
            .container
            .provideFavoritesViewModelFactory()
    }

    private lateinit var rvFavorites: RecyclerView
    private lateinit var adapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout directly instead of using binding
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find RecyclerView by ID
        rvFavorites = view.findViewById(R.id.rvFavorites)

        adapter = MealAdapter(
            meals = mutableListOf(),
            onItemClick = { meal ->
                // Navigate to detail from Favorites
                val action = FavoritesFragmentDirections
                    .actionFavoritesFragmentToRecipeDetailFragment(meal.id)
                view.findNavController().navigate(action)
            },
            onFavoriteClick = { meal ->
                // Remove from DB
                viewModel.remove(meal)
            }
        )

        rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        rvFavorites.adapter = adapter

        // Collecting favorites list from VM
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { list ->
                    adapter.updateMeals(list)
                }
            }
        }
    }
}
