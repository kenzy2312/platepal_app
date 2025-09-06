package com.example.recipeapp.data.domain

data class MealDetail(
    val id: String,
    val name: String,
    val category: String?,
    val thumbUrl: String?,
    val instructions: String?,
    val youtubeUrl: String?
)