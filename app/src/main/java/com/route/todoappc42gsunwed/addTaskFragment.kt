package com.route.todoappc42gsunwed

import android.app.DatePickerDialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import model.TaskModel
import roomDataBase.AppDatabase
import java.util.*

class AddTaskFragment(private val listener: (TaskModel) -> Unit) : Fragment() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var selectDateButton: Button
    private lateinit var saveButton: Button

    private var selectedDate: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frgment_add_task, container, false)

        titleInput = view.findViewById(R.id.titleInput)
        descriptionInput = view.findViewById(R.id.descriptionInput)
        selectDateButton = view.findViewById(R.id.pickTime)
        saveButton = view.findViewById(R.id.saveTask)

        selectDateButton.setOnClickListener {
            val c = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                    selectDateButton.text = selectedDate
                },
                c.get(Calendar.YEAR),        // السنة الحالية
                c.get(Calendar.MONTH),       // الشهر الحالي (انتبه month يبدأ من 0)
                c.get(Calendar.DAY_OF_MONTH) // اليوم الحالي
            )
            datePicker.show()
        }


        saveButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()

            if(title.isNotEmpty() && description.isNotEmpty() && selectedDate.isNotEmpty()) {
                val task = TaskModel(title=title, description = description, date = selectedDate,)
                listener(task)
                parentFragmentManager.popBackStack()
                addTask(task );
            }

        }


        return view

    }
    fun addTask(task: TaskModel){
        val db = AppDatabase.getDatabase(requireContext())
        val taskDao = db.taskDao()
        val task = task
        lifecycleScope.launch {
            taskDao.insertTask(task)
        }


    }
}
