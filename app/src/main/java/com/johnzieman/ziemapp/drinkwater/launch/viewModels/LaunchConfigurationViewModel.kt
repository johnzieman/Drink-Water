package com.johnzieman.ziemapp.drinkwater.launch.viewModels

import androidx.lifecycle.ViewModel

class LaunchConfigurationViewModel: ViewModel() {
    val heightMetrics = listOf("cm", "inch")
    val weightMetrics = listOf("kg", "lbs")
    val genders = listOf("Male", "Female")
}