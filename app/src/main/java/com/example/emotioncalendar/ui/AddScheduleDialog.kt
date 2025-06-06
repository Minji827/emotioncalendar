// AddScheduleDialog.kt
package com.example.emotioncalendar.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.emotioncalendar.data.AppDatabase
import com.example.emotioncalendar.data.Schedule
import com.example.emotioncalendar.databinding.DialogAddScheduleBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddScheduleDialog(
    context: Context,
    private val date: LocalDate,
    private val onSaved: () -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogAddScheduleBinding
    private var selectedEmotion: String = "😊"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddScheduleBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.dateTextView.text = date.toString()

        // 감정 버튼 클릭 리스너
        val emotionMap = mapOf(
            binding.emotionHappy to "😊",
            binding.emotionSad to "😢",
            binding.emotionAngry to "😡",
            binding.emotionNeutral to "😐",
            binding.emotionExcited to "😍"
        )

        for ((button, emoji) in emotionMap) {
            button.setOnClickListener {
                selectedEmotion = emoji
                binding.selectedEmotionText.text = emoji
            }
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val memo = binding.memoEditText.text.toString()

            if (title.isBlank()) {
                Toast.makeText(context, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val schedule = Schedule(
                title = title,
                memo = memo,
                emotion = selectedEmotion,
                startDate = date,
                endDate = date,
                alertTime = null
            )

            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.getDatabase(context).scheduleDao().insert(schedule)
                launch(Dispatchers.Main) {
                    onSaved()
                    dismiss()
                }
            }
        }
    }
}
