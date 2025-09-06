package com.example.recipeapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.app.RecipeApp
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.ui.MealAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val vm: HomeViewModel by viewModels {
        (requireActivity().application as RecipeApp)
            .container.provideHomeViewModelFactory()
    }
    private lateinit var rvMeals: RecyclerView
    private lateinit var txtSuggestedMeal: TextView
    private lateinit var imgSuggestedMeal: ImageView
    private lateinit var cardSuggestedMeal: View

    private lateinit var adapter: MealAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMeals = view.findViewById(R.id.rvMeals)
        txtSuggestedMeal = view.findViewById(R.id.txtSuggestedMeal)
        imgSuggestedMeal = view.findViewById(R.id.imgSuggestedMeal)
        cardSuggestedMeal = view.findViewById(R.id.cardSuggestedMeal)

        adapter = MealAdapter(
            meals = mutableListOf(),
            onItemClick = { meal -> navigateToDetail(meal) },
            onFavoriteClick = { meal -> vm.toggleFavorite(meal) }
        )

        rvMeals.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvMeals.adapter = adapter

        collectUi()
        setupSuggestedCardClicks()
    }
    private fun setupSuggestedCardClicks() {
        cardSuggestedMeal.setOnClickListener {
            val state = vm.ui.value
            if (state is HomeUiState.Loaded && state.suggested != null) {
                navigateToDetail(state.suggested)
            }
        }
    }

    private fun navigateToDetail(meal: Meal) {
        val action = HomeFragmentDirections
            .actionHomeFragmentToRecipeDetailFragment(meal.id)
        findNavController().navigate(action)
    }

    private fun collectUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui.collect { state ->
                    when (state) {
                        is HomeUiState.Loading -> {
                            // showing progress
                        }
                        is HomeUiState.Error -> {
                            // show an error
                        }
                        is HomeUiState.Loaded -> {
                            // suggested block
                            val s = state.suggested
                            if (s != null) {
                                txtSuggestedMeal.text = s.name
                                Glide.with(imgSuggestedMeal)
                                    .load(s.thumbUrl)
                                    .placeholder(R.drawable.placeholder_recipe)
                                    .into(imgSuggestedMeal)
                            } else {
                                txtSuggestedMeal.text = ""
                                imgSuggestedMeal.setImageDrawable(null)
                            }
                            // horizontal list
                            adapter.updateMeals(state.list)
                        }
                    }
                }
            }
        }
    }

}
