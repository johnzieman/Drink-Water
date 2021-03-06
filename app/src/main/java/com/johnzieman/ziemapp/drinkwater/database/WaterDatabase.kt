package com.johnzieman.ziemapp.drinkwater.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.johnzieman.ziemapp.drinkwater.models.DailyStory
import com.johnzieman.ziemapp.drinkwater.models.User
import com.johnzieman.ziemapp.drinkwater.models.WaterDaily

@Database(entities = [User::class, WaterDaily::class, DailyStory::class], version = 3, exportSchema = false)
@TypeConverters(UserConverters::class)
abstract class WaterDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getDailyWaterDao(): WaterDao
    abstract fun getDailyHistoryDao(): DailyHistoryDao
}