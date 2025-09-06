package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.app.RecipeApp
import com.example.recipeapp.data.FavoritesManager
import com.example.recipeapp.data.domain.Meal

class HomeFragment : Fragment() {

    private lateinit var adapter: MealsAdapter
    private val meals = mutableListOf<Meal>()

    private lateinit var rvMeals: RecyclerView
    private lateinit var txtSuggestedMeal: TextView
    private lateinit var imgSuggestedMeal: ImageView

    private val homeViewModel: HomeViewModel by viewModels {
        (requireActivity().application as RecipeApp)
            .container.provideHomeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout directly
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        // Initialize views using findViewById
        rvMeals = view.findViewById(R.id.rvMeals)
        txtSuggestedMeal = view.findViewById(R.id.txtSuggestedMeal)
        imgSuggestedMeal = view.findViewById(R.id.imgSuggestedMeal)

        // Adapter for RecyclerView
        adapter = MealsAdapter(meals) { meal: Meal ->
            FavoritesManager.addMeal(meal)
        }

        rvMeals.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvMeals.adapter = adapter

        // Observe Suggested Meal
        homeViewModel.randomMeal.observe(viewLifecycleOwner) { meal ->
            txtSuggestedMeal.text = meal.name
            Glide.with(imgSuggestedMeal)
                .load(meal.thumbUrl)
                .into(imgSuggestedMeal)
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
}
