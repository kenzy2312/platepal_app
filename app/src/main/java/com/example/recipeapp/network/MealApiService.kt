package com.example.recipeapp.network

import com.example.recipeapp.data.dto.MealListDto
import com.example.recipeapp.dto.CategoryResponseDto
import com.example.recipeapp.dto.MealsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// data/remote/NetworkServices.kt
interface MealApiService {
    @GET("search.php")
    suspend fun searchByName(@Query("s") query: String): MealsResponseDto

    // first-letter search (keep for future "starts with" mode)
    @GET("search.php")
    suspend fun searchByFirstLetter(@Query("f") letter: String): MealsResponseDto

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponseDto>

    @GET("filter.php")
    suspend fun getByCategory(@Query("c") category: String): Response<MealListDto>

    @GET("lookup.php")
    suspend fun getById(@Query("i") id: String): MealsResponseDto

    @GET("random.php")
    suspend fun getRandomMeal(): MealsResponseDto

    @GET("search.php?s=")
    suspend fun getAllMeals(): MealsResponseDto



}