package com.example.recipeapp.ui

import androidx.lifecycle.*
import com.example.recipeapp.data.domain.Meal
import com.example.recipeapp.data.repository.MealRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MealRepository
) : ViewModel() {


    private val _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal> get() = _randomMeal


    private val _mealsList = MutableLiveData<List<Meal>>()
    val mealsList: LiveData<List<Meal>> get() = _mealsList

    fun fetchRandomMeal() {
        viewModelScope.launch {
            try {
                val meal = repository.getRandomMeal()
                _randomMeal.postValue(meal)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun fetchAllMeals() {
        viewModelScope.launch {
            try {
                val meals = repository.getAllMeals()
                _mealsList.postValue(meals)
            } catch (e: Exception) {
                _mealsList.postValue(emptyList())
                e.printStackTrace()
            }
        }
    }
}


//class HomeViewModelFactory(
//    private val repository: MealRepository
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return HomeViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
