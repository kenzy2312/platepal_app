package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.app.RecipeApp
import com.example.recipeapp.data.FavoritesManager
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MealsAdapter
    private val meals = mutableListOf<Meal>()

    private val homeViewModel: HomeViewModel by viewModels {
        (requireActivity().application as RecipeApp)
            .container.provideHomeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        // Adapter for RecyclerView
        adapter = MealsAdapter(meals) { meal: Meal ->
            FavoritesManager.addMeal(meal)
        }


        binding.rvMeals.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvMeals.adapter = adapter

        // Observe Suggested Meal
        homeViewModel.randomMeal.observe(viewLifecycleOwner) { meal ->
            binding.txtSuggestedMeal.text = meal.name
            Glide.with(binding.imgSuggestedMeal)
                .load(meal.thumbUrl)
                .into(binding.imgSuggestedMeal)
        }

        // Observe Meals List
        homeViewModel.mealsList.observe(viewLifecycleOwner) { list ->
            meals.clear()
            meals.addAll(list)
            adapter.notifyDataSetChanged()
        }

        // Fetch Data from API
        homeViewModel.fetchRandomMeal()
        homeViewModel.fetchAllMeals()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
