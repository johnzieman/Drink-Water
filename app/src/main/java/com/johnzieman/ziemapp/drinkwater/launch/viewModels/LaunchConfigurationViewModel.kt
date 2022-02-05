package com.johnzieman.ziemapp.drinkwater.launch.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.johnzieman.ziemapp.drinkwater.UserRepository
import com.johnzieman.ziemapp.drinkwater.models.User
import java.util.*

class LaunchConfigurationViewModel : ViewModel() {
    val heightMetrics = listOf("cm", "inch")
    val weightMetrics = listOf("kg", "lbs")
    val genders = listOf("Male", "Female")

    private val userRepository = UserRepository.get()

    fun addUser(user: User) {
        userRepository.addUser(user)
    }
}