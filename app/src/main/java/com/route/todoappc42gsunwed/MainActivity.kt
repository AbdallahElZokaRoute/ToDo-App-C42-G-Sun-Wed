package com.route.todoappc42gsunwed

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.route.todoappc42gsunwed.databinding.ActivityMainBinding
import com.route.todoappc42gsunwed.fragments.addTodo.AddTodoBottomSheetFragment
import com.route.todoappc42gsunwed.fragments.addTodo.OnTaskAddedListener
import com.route.todoappc42gsunwed.fragments.settings.SettingsFragment
import com.route.todoappc42gsunwed.fragments.todosList.TodosListFragment
import com.route.todoappc42gsunwed.fragments.todosList.adapter.TasksAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todosListFragment: TodosListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    // Callbacks -> Recycler View
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.todoFragmentContainer.id, fragment).commit()
    }

    private fun initViews() {
        todosListFragment = TodosListFragment()
        binding.fabMain.setOnClickListener {
            val bottomSheetFragment = AddTodoBottomSheetFragment(listener = object :
                OnTaskAddedListener {
                override fun onTaskAdded() {
                    todosListFragment.getAllTasks()
                }
            })
            bottomSheetFragment.show(supportFragmentManager, "AddTodoBottomSheet")
        }
        binding.todoBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_list -> showFragment(todosListFragment)
                R.id.navigation_settings -> showFragment(SettingsFragment())
            }
            return@setOnItemSelectedListener true
        }
        binding.todoBottomNavigationView.selectedItemId = R.id.navigation_list
    }
}