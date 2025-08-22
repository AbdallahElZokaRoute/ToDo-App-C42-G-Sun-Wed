package com.route.todoappc42gsunwed.fragments.addTodo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todoappc42gsunwed.R
import com.route.todoappc42gsunwed.database.TasksDataBase
import com.route.todoappc42gsunwed.database.model.TaskDM
import com.route.todoappc42gsunwed.databinding.FragmentAddTodoBinding
import com.route.todoappc42gsunwed.fragments.callback.OnTaskAddedListener
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

class AddTodoBottomSheetFragment(
    // 2-
    private var onTaskAddedListener: OnTaskAddedListener? = null
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTodoBinding

    private lateinit var calendar: Calendar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        binding.addTodoButton.setOnClickListener {
            if (validateFields()) {
                TasksDataBase.getInstance(requireContext()).getTasksDao().insertTask(
                    TaskDM(
                        null,
                        title = binding.titleEditText.text?.toString() ?: "",
                        description = binding.descriptionEditText.text?.toString() ?: "",
                        date = LocalDate.ofInstant(
                            Instant.ofEpochMilli(calendar.timeInMillis),
                            ZoneId.systemDefault()
                        )

                    )
                )
                //3-
                onTaskAddedListener?.onTaskAdded()
                dismiss()
            }
        }
        binding.timeTextView.setOnClickListener {
            showTimeDialog()
        }
        binding.dateTextView.setOnClickListener {
            showDateDialog()
        }
    }

    private fun showTimeDialog() {
        val dialog =
            TimePickerDialog(
                requireContext(),
                object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        binding.timeTextView.text = "$hourOfDay:$minute"
                    }
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
                object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        binding.dateTextView.text = "$dayOfMonth - $month - $year"
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        dialog.show()
    }

    private fun validateFields(): Boolean {

        if (binding.titleEditText.text.isEmpty() || binding.titleEditText.text.isBlank()) {
            binding.titleEditText.error = getString(R.string.title_required)
            return false
        }
        if (binding.descriptionEditText.text.isEmpty()) {
            binding.descriptionEditText.error = getString(R.string.desc_required)
            return false
        }
        if (binding.dateTextView.text == getString(R.string.please_select_task_date)) {
            Toast.makeText(requireContext(), getString(R.string.date_required), Toast.LENGTH_LONG)
                .show()
            return false
        }
        if (binding.timeTextView.text == getString(R.string.please_select_task_time)) {
            Toast.makeText(requireContext(), getString(R.string.time_required), Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }
}
