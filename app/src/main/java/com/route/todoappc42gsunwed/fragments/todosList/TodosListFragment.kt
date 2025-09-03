package com.route.todoappc42gsunwed.fragments.todosList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.WeekDayBinder
import com.kizitonwose.calendar.view.WeekHeaderFooterBinder
import com.route.todoappc42gsunwed.R
import com.route.todoappc42gsunwed.database.TasksDataBase
import com.route.todoappc42gsunwed.database.model.TaskDM
import com.route.todoappc42gsunwed.databinding.FragmentTodosListBinding
import com.route.todoappc42gsunwed.databinding.ItemWeekDayBinding
import com.route.todoappc42gsunwed.databinding.ItemWeekHeaderBinding
import com.route.todoappc42gsunwed.fragments.callback.OnTaskClickListener
import com.route.todoappc42gsunwed.fragments.todosList.adapter.TasksAdapter
import com.route.todoappc42gsunwed.fragments.todosList.adapter.WeekDayHeaderViewHolder
import com.route.todoappc42gsunwed.fragments.todosList.adapter.WeekDayViewHolder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class TodosListFragment : Fragment() {
    private lateinit var binding: FragmentTodosListBinding
    private lateinit var adapter: TasksAdapter
    private lateinit var tasksList: List<TaskDM>
    var onTaskClickListener: OnTaskClickListener? = null
    private var selectedDate: LocalDate? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodosListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TasksAdapter(mutableListOf())
        //  Github Library
        adapter.onTaskClickListener = onTaskClickListener
        adapter.onCheckClickListener = object : OnTaskClickListener {
            override fun onTaskClick(
                task: TaskDM,
                position: Int
            ) {
                TasksDataBase.getInstance(requireContext()).getTasksDao()
                    .updateTask(task.copy(isDone = true))
                adapter.updateTaskState(position)

            }
        }
        adapter.onDeleteClickListener = object : OnTaskClickListener {
            override fun onTaskClick(task: TaskDM, position: Int) {
                TasksDataBase.getInstance(requireContext()).getTasksDao().deleteTask(task)
                adapter.deleteTaskState(position)
            }
        }
        getTasksByDate()
        binding.tasksRecyclerView.adapter = adapter
        initCalendarView()
    }

    fun initCalendarView() {
        binding.weekCalendarView.weekHeaderBinder =
            object : WeekHeaderFooterBinder<WeekDayHeaderViewHolder> {
                override fun create(view: View): WeekDayHeaderViewHolder {
                    val binding = ItemWeekHeaderBinding.bind(view)
                    return WeekDayHeaderViewHolder(binding)
                }

                override fun bind(container: WeekDayHeaderViewHolder, data: Week) {
                    container.binding.monthNameTextView.text = data.days.get(0).date.month.name
                }
            }
        binding.weekCalendarView.dayBinder = object : WeekDayBinder<WeekDayViewHolder> {
            override fun create(view: View): WeekDayViewHolder {
                val binding = ItemWeekDayBinding.bind(view)
                return WeekDayViewHolder(binding)
            }

            override fun bind(
                container: WeekDayViewHolder,
                data: WeekDay
            ) {
                val weekDayTextView = container.binding.weekDayNameTextView
                val monthDayTextView = container.binding.monthDayTextView
                monthDayTextView.text = "${data.date.dayOfMonth}"
                weekDayTextView.text =
                    data.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                if (selectedDate?.compareTo(data.date) == 0) {
                    monthDayTextView.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.blue,
                            null
                        )
                    )
                    weekDayTextView.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.blue,
                            null
                        )
                    )
                } else {
                    monthDayTextView.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.black,
                            null
                        )
                    )
                    weekDayTextView.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.black,
                            null
                        )
                    )
                }
                container.binding.root.setOnClickListener {
                    if (data.date.year == selectedDate?.year && data.date.month == selectedDate?.month && data.date.dayOfMonth == selectedDate?.dayOfMonth) {
                        selectedDate = null
                        binding.weekCalendarView.notifyWeekChanged(data)
                    } else {
                        val tempDate = selectedDate
                        selectedDate = data.date
                        binding.weekCalendarView.notifyDayChanged(data)
                        if (tempDate != null)
                            binding.weekCalendarView.notifyWeekChanged(tempDate)
                    }
                    getTasksByDate()
                }
            }
        }
        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(12).atStartOfMonth() // Adjust as needed
        val endDate = currentMonth.plusMonths(24).atEndOfMonth() // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        binding.weekCalendarView.setup(startDate, endDate, firstDayOfWeek)
        binding.weekCalendarView.scrollToWeek(currentDate)
    }

    fun getTasksByDate() {
        var tasks = TasksDataBase.getInstance(requireContext()).getTasksDao().getAllTasks()
        if (selectedDate != null) {
            Log.e("TAG", "getTasksByDate: selectedDate != null   $selectedDate")
            tasks = tasks.filter { task ->
                Log.e("TAG", "getTasksByDate: task.date = > ${task.date}")
                task.date?.year == selectedDate?.year && task.date?.month == selectedDate?.month && task.date?.dayOfMonth == selectedDate?.dayOfMonth
            }
        }
        adapter.setNewTasksList(tasks.toMutableList())
    }
}
