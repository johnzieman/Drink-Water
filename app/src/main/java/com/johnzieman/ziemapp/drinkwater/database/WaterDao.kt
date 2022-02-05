package com.johnzieman.ziemapp.drinkwater.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.johnzieman.ziemapp.drinkwater.models.User
import com.johnzieman.ziemapp.drinkwater.models.WaterDaily
import java.util.*

@Dao
interface WaterDao {
    @Query("SELECT * FROM water_daily")
    fun getAllDays(): LiveData<List<WaterDaily>>

    @Query("SELECT * FROM water_daily WHERE id=(:id)")
    fun getDay(id: UUID): LiveData<WaterDaily?>

    @Insert
    fun addAnotherDay(waterDaily: WaterDaily)

    @Update
    fun updateDailyWater(waterDaily: WaterDaily)

    @Delete
    fun deleteDay(waterDaily: WaterDaily)
}