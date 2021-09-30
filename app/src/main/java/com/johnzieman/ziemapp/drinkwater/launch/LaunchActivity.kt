package com.johnzieman.ziemapp.drinkwater.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.johnzieman.ziemapp.drinkwater.R
import com.johnzieman.ziemapp.drinkwater.interfaces.OnLauncherOpener

class LaunchActivity : AppCompatActivity(), OnLauncherOpener {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        supportActionBar?.hide()
        navController = Navigation.findNavController(this, R.id.nav_host_launcher)
    }

    override fun OnOpenUserConfig() {
        navController.navigate(R.id.action_launcherFragment_to_launcherConfigurationFragment)
    }
}