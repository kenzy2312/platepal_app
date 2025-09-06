package com.example.recipeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.data.domain.Meal

class MealsAdapter(
    private val meals: MutableList<Meal>,
    private val onFavoriteClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {

    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.txtMeal)
        val recipeCategory: TextView = view.findViewById(R.id.tvRecipeCategory)
        val recipeImage: ImageView = view.findViewById(R.id.imgSuggestedMeal)
        val cardRoot: View = view.findViewById(R.id.cardRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal_result, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]

        holder.recipeName.text = meal.name
        holder.recipeCategory.text = meal.category ?: "—"

        Glide.with(holder.itemView.context)
            .load(meal.thumbUrl)
            .apply(
                RequestOptions()
                    .centerCrop() // matches 4:3 image box
                    .placeholder(R.drawable.placeholder_recipe)
                    .error(R.drawable.placeholder_recipe)
            )
            .into(holder.recipeImage)

        holder.cardRoot.setOnClickListener {
            // TODO: will replace with real directions the recipe details fragment later.
            // Example (adjust to your nav graph):
             val action = SearchFragmentDirections
                 .actionSearchFragmentToRecipeDetailFragment(
                     mealId = meal.id,
                     mealName = meal.name,
                     mealThumbUrl = meal.thumbUrl ?: ""
                 )
             it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = meals.size

    fun updateMeals(newMeals: List<Meal>) {
        meals.clear()
        meals.addAll(newMeals)
        notifyDataSetChanged()
    }
}