package com.johnzieman.ziemapp.drinkwater.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id:UUID = UUID.randomUUID(),
    val isConfigured: Int,
    var userName: String,
    var userAge: String,
    var userHeight: String,
    var userWeight: String,
    var userSex: String,
    var weightMetrics: String,
    var heightMetrics: String
)