package com.johnzieman.ziemapp.drinkwater

import android.app.Application

class WaterApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        UserRepository.initialize(this)
        WaterRepository.initialize(this)
    }
}