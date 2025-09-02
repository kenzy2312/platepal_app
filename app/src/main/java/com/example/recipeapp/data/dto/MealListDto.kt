package com.example.recipeapp.data.dto

data class MealListDto(val meals: List<MealLiteDto>?)
data class MealLiteDto(
    val idMeal: String,
    val strMeal: String?,
    val strMealThumb: String?
)