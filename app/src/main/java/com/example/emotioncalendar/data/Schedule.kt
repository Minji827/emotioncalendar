// Schedule.kt
package com.example.emotioncalendar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val memo: String,
    val emotion: String, // 😊 😢 😡 😐 😍
    val startDate: LocalDate,
    val endDate: LocalDate,
    val alertTime: LocalTime?
)
