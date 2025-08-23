package com.route.todoappc42gsunwed.taskedit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.route.todoappc42gsunwed.database.TasksDataBase
import com.route.todoappc42gsunwed.database.model.TaskDM
import com.route.todoappc42gsunwed.databinding.ActivityEditTaskTodoBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditTaskTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskTodoBinding
    private lateinit var taskInt: TaskDM
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        taskInt = IntentCompat.getParcelableExtra(intent, "TASK_ITEM", TaskDM::class.java) as TaskDM


        binding.editTitleText.setText(taskInt.title)
        binding.editDescriptionText.setText(taskInt.description)
        calendar.time = taskInt.date ?: Date()

        binding.editDateTextView.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
        binding.editTimeTextView.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

        binding.editDateTextView.setOnClickListener {
            showDateDialog()
        }

        binding.editTimeTextView.setOnClickListener {
            showTimeDialog()
        }

        binding.saveChangesTodoButton.setOnClickListener {
            val newTitle = binding.editTitleText.text.toString()
            val newDescription = binding.editDescriptionText.text.toString()

            if (newTitle.isBlank()) {
                binding.editTitleText.error = "Title is Required!"
                return@setOnClickListener
            }

            val dao = TasksDataBase.getInstance(this).getTasksDao()
            taskInt.title = newTitle
            taskInt.description = newDescription
            taskInt.date = calendar.time
            dao.updateTask(taskInt)

            Toast.makeText(this, "Task Updated Successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    @SuppressLint("DefaultLocale")
    private fun showTimeDialog() {
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                binding.editTimeTextView.text = String.format("%02d:%02d", hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    @SuppressLint("DefaultLocale")
    private fun showDateDialog() {
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.editDateTextView.text = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
//
