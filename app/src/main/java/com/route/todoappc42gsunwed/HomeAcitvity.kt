package com.route.todoappc42gsunwed

import adaptar.CalnderAdapter
import adaptar.TaskAdapter
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import model.CalderModel
import model.TaskModel
import model.getDaysOfMonth
import roomDataBase.AppDatabase

class HomeAcitvity : AppCompatActivity(), CalnderAdapter.OnItemClickListener{

    private var selectedDate: String? = null
    private val tasks = mutableListOf<TaskModel>()
    private lateinit var taskAdapter: TaskAdapter

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val calendarRecyclerView = findViewById<RecyclerView>(R.id.calnder_recyclerView)
        calendarRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        calendarRecyclerView.adapter = CalnderAdapter(getDaysOfMonth(2025, 8), this)

        // إعداد RecyclerView للمهام
        val taskRecyclerView = findViewById<RecyclerView>(R.id.task_recyclerView)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(tasks){
            task,position->
            val editFragment = EditTaskFragment(task)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_edit, editFragment) // ضع Fragment في المكان
                .addToBackStack(null) // إضافة للعودة بالزر الخلفي
                .commit()
        }
        taskRecyclerView.adapter = taskAdapter


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val fragment = AddTaskFragment { newTask ->
                if (newTask.date == selectedDate) {
                    tasks.add(newTask)
                    taskAdapter.notifyItemInserted(tasks.size - 1)
                }

            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, fragment)
                .addToBackStack(null)
                .commit()

            fab.hide()
        }


        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                fab.show()
                lifecycleScope.launch {
                    val allTasks = getAllTasksbyDate(selectedDate ?: "")
                    tasks.clear()
                    tasks.addAll(allTasks)
                    taskAdapter.notifyDataSetChanged()
                }
            }
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_settings -> {
                    openFragment(SettingFragment())
                    true
                }
                else -> {
                    // أي أيقونة أخرى أو الضغط المتكرر على Home
                    supportFragmentManager.popBackStack(null, 0)
                    true
                }
            }

        }




    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(item: CalderModel) {
        selectedDate="${item.day}/${item.month}/${item.year}"
        Log.d("HomeActivity", "Calendar item clicked: ${item.day}/${item.month}/${item.year}")
        lifecycleScope.launch {
            val allTasks = getAllTasksbyDate("${item.day}/${item.month}/${item.year}")
            tasks.clear()
            tasks.addAll(allTasks)
            taskAdapter.notifyDataSetChanged()
        }

    }

    override  fun OnItemHoldClickListener(task: TaskModel){

    }
    private suspend fun getAllTasksbyDate(selectDate:String): List<TaskModel> {
        val db = AppDatabase.getDatabase(this)
        return db.taskDao().getTasksByDate(selectDate)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, fragment) // المكان اللي يظهر فيه الـ fragment
            .addToBackStack(null)
            .commit()
    }

}
