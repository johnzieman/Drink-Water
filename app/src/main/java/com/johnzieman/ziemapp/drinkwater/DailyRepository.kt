package com.johnzieman.ziemapp.drinkwater

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.johnzieman.ziemapp.drinkwater.database.WaterDatabase
import com.johnzieman.ziemapp.drinkwater.models.DailyStory
import com.johnzieman.ziemapp.drinkwater.models.WaterDaily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

private const val DB_NAME = "daily"

class DailyRepository private constructor(context: Context) {
    private val job = SupervisorJob()
    private val ioScope = CoroutineScope(job + Dispatchers.IO)

    private val datebase: WaterDatabase = Room.databaseBuilder(
        context.applicationContext,
        WaterDatabase::class.java,
        DB_NAME
    ).build()

    private val waterDao = datebase.getDailyHistoryDao()

    fun getWholeList(): LiveData<List<DailyStory>> = waterDao.getDailyHistories()

    fun getItem(id: UUID): LiveData<DailyStory?> = waterDao.getDailyHistory(id)

    fun addAnotherDay(dailyStory: DailyStory) {
        ioScope.launch {
            waterDao.addDailyHistory(dailyStory)
        }
    }

    fun updateHistoryList(dailyStory: DailyStory) {
        ioScope.launch {
            waterDao.updateDailyHistory(dailyStory)
        }
    }

    fun deleteDay(dailyStory: DailyStory) {
        ioScope.launch {
            waterDao.deleteDailyHistory(dailyStory)
        }
    }

    companion object {
        private var INSTANCE: DailyRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DailyRepository(context)
            }
        }

        fun get(): DailyRepository =
            INSTANCE ?: throw IllegalStateException("WaterRepository must be initialized")
    }

}