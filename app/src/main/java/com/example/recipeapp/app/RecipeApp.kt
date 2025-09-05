package com.example.recipeapp.app

import android.app.Application

class RecipeApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(applicationContext) // build Retrofit, repos, use cases here
    }
}