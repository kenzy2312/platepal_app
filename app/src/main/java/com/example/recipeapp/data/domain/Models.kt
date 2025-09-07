package com.example.recipeapp.data.domain


data class Meal(
    val id: String,
    val name: String,
    val category: String?,
    val thumbUrl: String?,
    var isFavorite: Boolean = false
)

