package com.example.recipeapp.data.domain

import com.example.recipeapp.dto.MealDto


fun MealDto.toDomain() = Meal(
    id = idMeal,
    name = strMeal,
    category = strCategory,
    thumbUrl = strMealThumb,
    isFavorite = false
)
fun MealDto.toDetail() = MealDetail(
    id = idMeal,
    name = strMeal,
    category = strCategory,
    thumbUrl = strMealThumb,
    instructions = strInstructions,
    youtubeUrl = strYoutube
)