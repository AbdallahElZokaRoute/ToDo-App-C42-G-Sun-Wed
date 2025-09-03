package com.route.todoappc42gsunwed.fragments.editTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.route.todoappc42gsunwed.database.model.TaskDM
import com.route.todoappc42gsunwed.databinding.FragmentEditTaskBinding
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

class EditTaskFragment(
    private val task: TaskDM,
    private val onSaveChangesClickList: (newTask: TaskDM) -> Unit
) : Fragment() {

    private lateinit var calendar: Calendar
    private lateinit var binding: FragmentEditTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()

        binding.titleEditText.setText(task.title)
        binding.descriptionEditText.setText(task.title)
        binding.dateTextView.text = task.date.toString()


        binding.toolbar.setNavigationOnClickListener { close() }
        binding.timeTextView.setOnClickListener { showTimeDialog() }
        binding.dateTextView.setOnClickListener { showDateDialog() }
        binding.saveButton.setOnClickListener {
            onSaveChangesClickList(
                TaskDM(
                    id = task.id,
                    title = binding.titleEditText.text?.toString() ?: "",
                    description = binding.descriptionEditText.text?.toString() ?: "",
                    date = LocalDate.ofInstant(
                        Instant.ofEpochMilli(calendar.timeInMillis),
                        ZoneId.systemDefault()
                    )
                )
            )
        }
    }

    private fun showTimeDialog() {
        val dialog =
            TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    binding.timeTextView.text = "$hourOfDay:$minute"
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
        dialog.show()
    }

    fun showDateDialog() {
        val dialog =
            DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    binding.dateTextView.text = "$dayOfMonth - $month - $year"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        dialog.show()
    }

    private fun close() {
        parentFragmentManager.popBackStack()
    }
}
