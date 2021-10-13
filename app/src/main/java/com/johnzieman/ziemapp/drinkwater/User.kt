package com.johnzieman.ziemapp.drinkwater

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id:UUID = UUID.randomUUID(),
    val isConfigured: Int,
    var userName: String,
    var userAge: Int,
    var userHeight: Int,
    var userWeight: Int,
    var userSex: String,
    var weightMetrics: String,
    var heightMetrics: String
)