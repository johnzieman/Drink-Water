package com.johnzieman.ziemapp.drinkwater

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.johnzieman.ziemapp.drinkwater.database.WaterDatabase
import com.johnzieman.ziemapp.drinkwater.models.User
import com.johnzieman.ziemapp.drinkwater.models.WaterDaily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "water_daily"

class WaterRepository private constructor(context: Context) {

    private val job = SupervisorJob()
    private val ioScope = CoroutineScope(job + Dispatchers.IO)

    private val datebase: WaterDatabase = Room.databaseBuilder(
        context.applicationContext,
        WaterDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val waterDao = datebase.getDailyWaterDao()

    fun getAllDay(): LiveData<List<WaterDaily>> = waterDao.getAllDays()

    fun getDay(id: UUID): LiveData<WaterDaily?> = waterDao.getDay(id)

    fun addAnotherDay(waterDaily: WaterDaily) {
        ioScope.launch {
            waterDao.addAnotherDay(waterDaily)
        }
    }

    fun updateDailyWater(waterDaily: WaterDaily) {
        ioScope.launch {
            waterDao.updateDailyWater(waterDaily)
        }
    }

    fun deleteDay(waterDaily: WaterDaily) {
        ioScope.launch {
            waterDao.deleteDay(waterDaily)
        }
    }

    companion object {
        private var INSTANCE: WaterRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = WaterRepository(context)
            }
        }

        fun get(): WaterRepository =
            INSTANCE ?: throw IllegalStateException("WaterRepository must be initialized")
    }
}