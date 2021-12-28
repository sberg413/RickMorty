package com.sberg413.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val navHostFragment=supportFragmentManager
                .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
            val navController=navHostFragment.navController
            NavigationUI.setupActionBarWithNavController(this, navController)
        }
    }
}