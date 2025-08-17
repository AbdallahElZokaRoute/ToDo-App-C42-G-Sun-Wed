package com.route.todoappc42gsunwed

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.route.todoappc42gsunwed.databinding.ActivityMainBinding
import com.route.todoappc42gsunwed.fragments.addTodo.AddTodoBottomSheetFragment
import com.route.todoappc42gsunwed.fragments.settings.SettingsFragment
import com.route.todoappc42gsunwed.fragments.todosList.TodosListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
        binding.fabMain.setOnClickListener {
            val bottomSheetFragment = AddTodoBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, "AddTodoBottomSheet")
        }
        binding.todoBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_list -> showFragment(TodosListFragment())
                R.id.navigation_settings -> showFragment(SettingsFragment())
            }
            return@setOnItemSelectedListener true
        }
        binding.todoBottomNavigationView.selectedItemId = R.id.navigation_list
    }
}