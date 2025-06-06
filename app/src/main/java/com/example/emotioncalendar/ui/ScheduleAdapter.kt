// ScheduleAdapter.kt
package com.example.emotioncalendar.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emotioncalendar.data.Schedule
import com.example.emotioncalendar.databinding.ItemScheduleBinding

class ScheduleAdapter(private val schedules: List<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {
            binding.titleTextView.text = schedule.title
            binding.memoTextView.text = schedule.memo
            binding.emotionTextView.text = schedule.emotion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedules[position])
    }

    override fun getItemCount(): Int = schedules.size
}
