package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.FavoritesManager

class FavoritesFragment : Fragment() {

    private lateinit var adapter: MealsAdapter
    private lateinit var rvFavorites: RecyclerView

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

        val favorites = FavoritesManager.getFavorites().toMutableList()

        adapter = MealsAdapter(favorites) { meal ->
            FavoritesManager.removeMeal(meal)
            favorites.remove(meal)
            adapter.notifyDataSetChanged()
        }

        rvFavorites.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvFavorites.adapter = adapter
    }
}
