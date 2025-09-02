package com.example.recipeapp.data.domain

import com.example.recipeapp.dto.MealDto


fun MealDto.toDomain() = Meal(
    id = idMeal,
    name = strMeal,
    category = strCategory,
    thumbUrl = strMealThumb
)
