package com.johnzieman.ziemapp.drinkwater

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.johnzieman.ziemapp.drinkwater.database.WaterDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "water_db"

class WaterRepository private constructor(context: Context) {
    private val datebase: WaterDatabase = Room.databaseBuilder(
        context.applicationContext,
        WaterDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val executor = Executors.newSingleThreadExecutor()
    private val waterDao = datebase.getUserDao()


    fun getUsers(): LiveData<List<User>> = waterDao.getUsers()
    fun getUser(id: UUID): LiveData<User?> = waterDao.getUser(id)
    fun addUser(user: User) {
        executor.execute {
            waterDao.addUser(user)
        }
    }

    fun updateUser(user: User) {
        executor.execute {
            waterDao.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        executor.execute {
            waterDao.deleteUser(user)
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
            INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
    }
}