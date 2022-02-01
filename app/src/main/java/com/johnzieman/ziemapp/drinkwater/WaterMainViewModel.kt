package com.johnzieman.ziemapp.drinkwater

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.johnzieman.ziemapp.drinkwater.models.User

class WaterMainViewModel : ViewModel() {
    private val waterRepository = WaterRepository.get()

    fun getUsers(): LiveData<List<User>> {
        return waterRepository.getUsers()
    }
}