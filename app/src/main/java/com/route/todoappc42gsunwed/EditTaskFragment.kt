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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditTaskFragment(private var task: TaskModel): Fragment() {

    private lateinit var titleEdit: EditText
    private lateinit var descriptionEdit: EditText
    private lateinit var editDateButton: Button
    private lateinit var saveEditButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_task, container, false)

        titleEdit = view.findViewById(R.id.titleEdit)
        descriptionEdit = view.findViewById(R.id.descriptionEdit)
        editDateButton = view.findViewById(R.id.pickTimeEdit)
        saveEditButton = view.findViewById(R.id.editTask)

        // وضع البيانات الحالية في الحقول
        titleEdit.setText(task.title)
        descriptionEdit.setText(task.description)
        editDateButton.text = task.date


        editDateButton.setOnClickListener {
            val c = Calendar.getInstance()
            // تحويل التاريخ من String إلى java.util.Date
            val date = convertDate(task.date!!)
            c.time = date

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth/${month + 1}/$year"
                    editDateButton.text = selectedDate
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // حفظ التعديل
        saveEditButton.setOnClickListener {
            val updatedTask = TaskModel(
                id = task.id,  // مهم عشان Room يعرف أي عنصر يحدث
                title = titleEdit.text.toString(),
                description = descriptionEdit.text.toString(),
                date = editDateButton.text.toString(),
                isDone = task.isDone
            )
            editTask(updatedTask)
            parentFragmentManager.popBackStack() // إغلاق الـ Fragment بعد الحفظ
        }

        return view
    }

    // تحويل String إلى java.util.Date
    private fun convertDate(dateString: String): java.util.Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.parse(dateString)!!
    }

    // تحديث Task في قاعدة البيانات
    private fun editTask(task: TaskModel) {
        val db = AppDatabase.getDatabase(requireContext())
        val taskDao = db.taskDao()
        lifecycleScope.launch {
            taskDao.updateTask(task)
        }
    }
}
