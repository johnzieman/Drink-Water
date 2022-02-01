package com.johnzieman.ziemapp.drinkwater.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.johnzieman.ziemapp.drinkwater.models.User

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(UserConverters::class)
abstract class WaterDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
}