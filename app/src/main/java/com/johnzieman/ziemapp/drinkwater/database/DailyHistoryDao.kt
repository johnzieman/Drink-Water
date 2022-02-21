package com.johnzieman.ziemapp.drinkwater.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.johnzieman.ziemapp.drinkwater.models.DailyStory
import com.johnzieman.ziemapp.drinkwater.models.User
import java.util.*
@Dao
interface DailyHistoryDao {
    @Query("SELECT * FROM daily")
    fun getDailyHistories(): LiveData<List<DailyStory>>
    @Query("SELECT * FROM daily WHERE id=(:id)")
    fun getDailyHistory(id: UUID): LiveData<DailyStory?>
    @Insert
    fun addDailyHistory(daily: DailyStory)
    @Update
    fun updateDailyHistory(daily: DailyStory)
    @Delete
    fun deleteDailyHistory(daily: DailyStory)
}