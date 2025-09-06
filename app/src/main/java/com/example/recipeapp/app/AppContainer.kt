package com.example.recipeapp.app

import com.example.recipeapp.data.domain.SearchMeals
import com.example.recipeapp.data.domain.SearchMealsImpl
import com.example.recipeapp.data.remote.RemoteDataSource
import com.example.recipeapp.data.remote.RemoteDataSourceImpl
import com.example.recipeapp.data.repository.MealRepository
import com.example.recipeapp.data.repository.MealRepositoryImpl
import com.example.recipeapp.network.MealApiService
import com.example.recipeapp.ui.detail.DetailViewModelFactory
import com.example.recipeapp.ui.search.SearchViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.recipeapp.ui.HomeViewModelFactory
class AppContainer {

class AppContainer(private val context: Context) {

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api: MealApiService by lazy {
        retrofit.create(MealApiService::class.java)
    }

    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImpl(api)
    }

    private val repository: MealRepository by lazy {
        MealRepositoryImpl(remoteDataSource)
    }

    private val searchMeals: SearchMeals by lazy {
        SearchMealsImpl(repository)
    }

    fun provideSearchViewModelFactory() = SearchViewModelFactory(searchMeals)
    fun provideHomeViewModelFactory() = HomeViewModelFactory(repository)


    private val db: AppDatabase by lazy {
        AppDatabase.getDatabase(context)
    }
    private val favoritesRepo by lazy { FavoritesRepositoryImpl(db.favoritesDao()) }
    private val getMealDetail by lazy { GetMealDetailImpl(repository) }

    fun provideRecipeDetailVMFactory() =
        DetailViewModelFactory(getMealDetail, favoritesRepo)
}