package com.johnzieman.ziemapp.drinkwater.launch.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.johnzieman.ziemapp.drinkwater.UserRepository
import com.johnzieman.ziemapp.drinkwater.WaterRepository
import com.johnzieman.ziemapp.drinkwater.models.User
import com.johnzieman.ziemapp.drinkwater.models.WaterDaily

class ResultViewModel : ViewModel() {
    private val waterRepository = WaterRepository.get()
    private val userRepository = UserRepository.get()
    fun addDay(waterDaily: WaterDaily) {
        waterRepository.addAnotherDay(waterDaily)
    }

    fun getUsers(): LiveData<List<User>> {
        return userRepository.getUsers()
    }

}