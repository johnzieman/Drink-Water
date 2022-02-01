package com.johnzieman.ziemapp.drinkwater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.johnzieman.ziemapp.drinkwater.interfaces.OnCheckRegistration
import com.johnzieman.ziemapp.drinkwater.interfaces.OnSaveUserResult
import com.johnzieman.ziemapp.drinkwater.launch.LaunchActivity

class MainActivity : AppCompatActivity(), OnCheckRegistration {
    lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        navController = Navigation.findNavController(this, R.id.nav_host)
        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onOpenLaunchFragment() {
        val intent = Intent(this, LaunchActivity::class.java)
        startActivity(intent)
        finish()
    }


}