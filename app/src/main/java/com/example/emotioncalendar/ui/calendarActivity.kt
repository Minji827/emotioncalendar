// CalendarActivity.kt
package com.example.emotioncalendar.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emotioncalendar.databinding.ActivityCalendarBinding
import com.example.emotioncalendar.data.AppDatabase
import com.example.emotioncalendar.data.Schedule
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var database: AppDatabase
    private var selectedDate: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        selectedDate = LocalDate.now()

        setupCalendar()
        setupRecyclerView()
        loadSchedulesForDate(selectedDate!!)

        binding.addScheduleButton.setOnClickListener {
            selectedDate?.let {
                AddScheduleDialog(this, it) {
                    loadSchedulesForDate(it)
                }.show()
            }
        }
    }

    private fun setupCalendar() {
        val currentMonth = YearMonth.now()
        val firstDayOfWeek = java.time.DayOfWeek.SUNDAY
        binding.calendarView.setup(currentMonth, currentMonth.plusMonths(1), firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)

        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer {
                return DayViewContainer(view)
            }

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.bind(day, selectedDate) { clickedDate ->
                    selectedDate = clickedDate
                    loadSchedulesForDate(clickedDate)
                    binding.calendarView.notifyCalendarChanged() // 갱신
                }
            }
        }

    }

    private fun setupRecyclerView() {
        binding.scheduleRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.scheduleRecyclerView.adapter = ScheduleAdapter(emptyList())
    }

    private fun loadSchedulesForDate(date: LocalDate) {
        lifecycleScope.launch {
            val schedules = database.scheduleDao().getSchedulesByDate(date.toString())
            binding.scheduleRecyclerView.adapter = ScheduleAdapter(schedules)
        }
    }
}
