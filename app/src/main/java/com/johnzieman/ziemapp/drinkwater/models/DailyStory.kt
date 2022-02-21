package com.johnzieman.ziemapp.drinkwater.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "daily")
data class DailyStory(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    var logo: Int = 0,
    var timeList: String? = "",
    var howMuchList: Float? = 1f
)