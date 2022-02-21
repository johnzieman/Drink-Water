package com.johnzieman.ziemapp.drinkwater

import android.app.Application
import android.content.Context
import com.cioccarellia.ksprefs.KsPrefs

class WaterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UserRepository.initialize(this)
        WaterRepository.initialize(this)
        DailyRepository.initialize(this)
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
        val prefs by lazy { KsPrefs(appContext) }
    }
}