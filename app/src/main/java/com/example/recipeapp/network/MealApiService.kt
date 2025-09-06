package com.example.recipeapp.network

import com.example.recipeapp.dto.MealsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    suspend fun searchByName(@Query("s") query: String): MealsResponseDto

    @GET("search.php")
    suspend fun searchByFirstLetter(@Query("f") letter: String): MealsResponseDto
    @GET("lookup.php")
    suspend fun getById(@Query("i") id: String): MealsResponseDto

    @GET("random.php")
    suspend fun getRandomMeal(): MealsResponseDto

    @GET("search.php?s=")
    suspend fun getAllMeals(): MealsResponseDto



}