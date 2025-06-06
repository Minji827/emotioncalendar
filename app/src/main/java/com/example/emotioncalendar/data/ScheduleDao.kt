// ScheduleDao.kt
package com.example.emotioncalendar.data

import androidx.room.*

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedules WHERE startDate = :date")
    suspend fun getSchedulesByDate(date: String): List<Schedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: Schedule)

    @Update
    suspend fun update(schedule: Schedule)

    @Delete
    suspend fun delete(schedule: Schedule)
}
