package com.johnzieman.ziemapp.drinkwater.launch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.johnzieman.ziemapp.drinkwater.MainActivity
import com.johnzieman.ziemapp.drinkwater.R
import com.johnzieman.ziemapp.drinkwater.interfaces.OnCheckRegistration
import com.johnzieman.ziemapp.drinkwater.interfaces.OnLauncherOpener
import com.johnzieman.ziemapp.drinkwater.interfaces.OnSaveUserResult

class LaunchActivity : AppCompatActivity(), OnLauncherOpener, OnSaveUserResult {
    lateinit var navController: NavController
    private var onSaveUserResult: OnSaveUserResult? = null


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

    override fun onLaunchMainFragment() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}