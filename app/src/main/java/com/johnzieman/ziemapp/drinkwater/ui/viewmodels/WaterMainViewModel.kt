package com.johnzieman.ziemapp.drinkwater.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.johnzieman.ziemapp.drinkwater.DailyRepository
import com.johnzieman.ziemapp.drinkwater.UserRepository
import com.johnzieman.ziemapp.drinkwater.WaterRepository
import com.johnzieman.ziemapp.drinkwater.models.DailyStory
import com.johnzieman.ziemapp.drinkwater.models.User
import com.johnzieman.ziemapp.drinkwater.models.WaterDaily

class WaterMainViewModel : ViewModel() {
    private val userRepository = UserRepository.get()
    private val waterRepository = WaterRepository.get()
    private val daily = DailyRepository.get()

    fun getUsers(): LiveData<List<User>> = userRepository.getUsers()

    fun getDays(): LiveData<List<WaterDaily>> = waterRepository.getAllDay()

    fun getHistoryList(): LiveData<List<DailyStory>> = daily.getWholeList()

    fun addOneDrinkHistory(dailyStory: DailyStory) {
        daily.addAnotherDay(dailyStory)
    }

    fun updateDay(waterDaily: WaterDaily) {
        waterRepository.updateDailyWater(waterDaily)
    }
}