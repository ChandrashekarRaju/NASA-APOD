package com.nasa.apod.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.nasa.apod.R
import com.nasa.apod.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.apodFragment,
                R.id.favoritesAPODFragment
            )
        )

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.setupWithNavController(navController)

    }
}