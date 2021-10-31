package com.johnzieman.ziemapp.drinkwater.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.johnzieman.ziemapp.drinkwater.MainActivity
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

    override fun onOpenUserConfig() {
        navController.navigate(R.id.action_launcherFragment_to_launcherConfigurationFragment)
    }

    override fun onSaveUserData() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOpenUSerResultFragment() {
        navController.navigate(R.id.action_launcherConfigurationFragment_to_resultFragment)
    }
}