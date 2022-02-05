package com.johnzieman.ziemapp.drinkwater.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "water_daily")
data class WaterDaily(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val dailyRate: Int,
    var drunk: Int,
    var cupsRate: Int,
    var cupDrunk: Int
)
