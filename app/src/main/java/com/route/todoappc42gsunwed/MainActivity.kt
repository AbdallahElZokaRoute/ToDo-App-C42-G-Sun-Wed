package com.route.todoappc42gsunwed

import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.route.todoappc42gsunwed.database.TasksDataBase
import com.route.todoappc42gsunwed.database.model.TaskDM
import com.route.todoappc42gsunwed.databinding.ActivityMainBinding
import com.route.todoappc42gsunwed.fragments.addTodo.AddTodoBottomSheetFragment
import com.route.todoappc42gsunwed.fragments.callback.OnTaskAddedListener
import com.route.todoappc42gsunwed.fragments.callback.OnTaskClickListener
import com.route.todoappc42gsunwed.fragments.editTask.EditTaskFragment
import com.route.todoappc42gsunwed.fragments.settings.SettingsFragment
import com.route.todoappc42gsunwed.fragments.todosList.TodosListFragment
import com.route.todoappc42gsunwed.shared_preferences.SettingsSharedPreferences

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todosListFragment: TodosListFragment
    private lateinit var sharedPreferences: SettingsSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = SettingsSharedPreferences(this)

        val currentLanguage = sharedPreferences.getLanguage()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.getSystemService(android.app.LocaleManager::class.java)?.applicationLocales =
                LocaleList.forLanguageTags(currentLanguage)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(currentLanguage)
            )
        }

        AppCompatDelegate.setDefaultNightMode(
            if (sharedPreferences.getMode() == "light") AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
        )

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

        todosListFragment.onTaskClickListener = object : OnTaskClickListener {
            override fun onTaskClick(
                task: TaskDM,
                position: Int
            ) {
                val editTaskFragment = EditTaskFragment(task) {
                    TasksDataBase
                        .getInstance(this@MainActivity)
                        .getTasksDao()
                        .updateTask(it)
                    supportFragmentManager.popBackStack()
                    todosListFragment.getTasksByDate()
                }

                supportFragmentManager
                    .beginTransaction()
                    .replace(binding.root.id, editTaskFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    // Callbacks -> Recycler View
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.todoFragmentContainer.id, fragment).commit()
    }

    private fun initViews() {
        todosListFragment = TodosListFragment()
        binding.fabMain.setOnClickListener {
            val bottomSheetFragment = AddTodoBottomSheetFragment(onTaskAddedListener = object :
                OnTaskAddedListener {
                override fun onTaskAdded() {
                    todosListFragment.getTasksByDate()
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
