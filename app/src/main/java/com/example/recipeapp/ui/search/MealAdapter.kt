package com.example.recipeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.data.domain.Meal

class MealAdapter(
    private val meals: MutableList<Meal>,
    private val onItemClick: (Meal) -> Unit,
    private val onFavoriteClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.tvRecipeName)
        val recipeCategory: TextView = view.findViewById(R.id.tvRecipeCategory)
        val recipeImage: ImageView = view.findViewById(R.id.ivRecipeImage)
        val btnFavorite: ImageButton = view.findViewById(R.id.btnFavorite)
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

        holder.btnFavorite.isClickable = true
        holder.btnFavorite.isFocusable = true

        val isFav = meal.isFavorite
        holder.btnFavorite.setImageResource(
            if (isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )
        holder.btnFavorite.setOnClickListener { onFavoriteClick(meal)
            // Optimistically flip UI state
            meal.isFavorite = !meal.isFavorite
            notifyItemChanged(position) }

        holder.cardRoot.setOnClickListener { onItemClick(meal) }
    }

    override fun getItemCount(): Int = meals.size

    fun updateMeals(newMeals: List<Meal>) {
        meals.clear()
        meals.addAll(newMeals)
        notifyDataSetChanged()
    }
}
