package com.example.recipeapp.data.domain

import com.example.recipeapp.dto.MealDto

data class Meal(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val thumbUrl: String,
    val youtubeUrl: String,
    val ingredients: List<String>,
    val measures: List<String>
)


fun MealDto.toDomain(): Meal {
    val ingredients = listOf(
        strIngredient1,
        strIngredient2,
        strIngredient3,
        strIngredient4,
        strIngredient5,
        strIngredient6,
        strIngredient7,
        strIngredient8,
        strIngredient9,
        strIngredient10,
        strIngredient11,
        strIngredient12,
        strIngredient13,
        strIngredient14,
        strIngredient15,
        strIngredient16,
        strIngredient17,
        strIngredient18,
        strIngredient19,
        strIngredient20
    ).filterNotNull().filter { it.isNotBlank() }

    val measures = listOf(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
    ).filterNotNull().filter { it.isNotBlank() }

    return Meal(
        id = idMeal,
        name = strMeal ?: "",
        category = strCategory ?: "",
        area = strArea ?: "",
        instructions = strInstructions ?: "",
        thumbUrl = strMealThumb ?: "",
        youtubeUrl = strYoutube ?: "",
        ingredients = ingredients,
        measures = measures
    )
}
