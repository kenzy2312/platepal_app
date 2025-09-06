package com.example.recipeapp.app

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.recipeapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.recipeapp.ui.MoreBottomSheet

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ⬇️ nav host fragment setup
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // ⬇️ handle navigation items
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.moreFragment -> {
                    // open bottom sheet instead of navigating
                    val bottomSheet = MoreBottomSheet()
                    bottomSheet.show(supportFragmentManager, "MoreBottomSheet")
                    false
                }
                else -> {
                    navController.navigate(item.itemId)
                    true
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutFragment -> {
                // navigate to AboutFragment
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.aboutFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
