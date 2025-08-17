package com.route.todoappc42gsunwed.fragments.todosList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.route.todoappc42gsunwed.database.TasksDataBase
import com.route.todoappc42gsunwed.database.model.TaskDM
import com.route.todoappc42gsunwed.databinding.FragmentTodosListBinding
import com.route.todoappc42gsunwed.fragments.todosList.adapter.TasksAdapter

class TodosListFragment : Fragment() {
    private lateinit var binding: FragmentTodosListBinding
    private lateinit var adapter: TasksAdapter
    private lateinit var tasksList: List<TaskDM>
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
        adapter = TasksAdapter(listOf())
        getAllTasks()
        binding.tasksRecyclerView.adapter = adapter
    }

    fun getAllTasks() {
        val tasks = TasksDataBase.getInstance(requireContext()).getTasksDao().getAllTasks()
        adapter.setNewTasksList(tasks)
    }
}
