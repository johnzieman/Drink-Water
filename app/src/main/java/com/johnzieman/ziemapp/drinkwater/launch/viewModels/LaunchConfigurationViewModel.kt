package com.johnzieman.ziemapp.drinkwater.launch.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.johnzieman.ziemapp.drinkwater.models.User
import com.johnzieman.ziemapp.drinkwater.WaterRepository
import java.util.*

class LaunchConfigurationViewModel : ViewModel() {
    val heightMetrics = listOf("cm", "inch")
    val weightMetrics = listOf("kg", "lbs")
    val genders = listOf("Male", "Female")

    private val waterRepository = WaterRepository.get()
    private val userIdLiveDate = MutableLiveData<UUID>()

    fun addUser(user: User) {
        waterRepository.addUser(user)
    }

    fun loadUser(uuid: UUID) {
        userIdLiveDate.value = uuid
    }

    val userLiveData: LiveData<User?> = Transformations.switchMap(userIdLiveDate){
            storyId-> waterRepository.getUser(storyId)
    }

    fun getUsers(): LiveData<List<User>> {
        return waterRepository.getUsers()
    }

}