package com.johnzieman.ziemapp.drinkwater.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.johnzieman.ziemapp.drinkwater.WaterRepository
import com.johnzieman.ziemapp.drinkwater.models.User

class WaterMainViewModel : ViewModel() {
    private val waterRepository = WaterRepository.get()

    fun getUsers(): LiveData<List<User>> {
        return waterRepository.getUsers()
    }
}