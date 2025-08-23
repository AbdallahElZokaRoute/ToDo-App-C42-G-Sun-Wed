package com.route.todoappc42gsunwed.fragments.todosList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.route.todoappc42gsunwed.database.TasksDataBase
import com.route.todoappc42gsunwed.database.model.TaskDM
import com.route.todoappc42gsunwed.databinding.FragmentTodosListBinding
import com.route.todoappc42gsunwed.fragments.todosList.adapter.TasksAdapter
import com.route.todoappc42gsunwed.taskedit.EditTaskTodoActivity
import java.time.LocalDate

class TodosListFragment : Fragment() {
    private  lateinit var binding: FragmentTodosListBinding
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
        adapter = TasksAdapter(mutableListOf()) { task->removeTask(task) }
        binding.tasksRecyclerView.adapter = adapter
        getAllTasks()
        adapter.onDoneClickListener = TasksAdapter.onTaskClickListener { pos, task ->
            val dao = TasksDataBase.getInstance(requireContext()).getTasksDao()
            dao.updateTask(task)
            adapter.notifyItemChanged(pos)
        }
        adapter.onItemClickListener = TasksAdapter.onTaskClickListener{pos,task->
            val intent = Intent(requireContext(), EditTaskTodoActivity::class.java)
            intent.putExtra("TASK_ITEM",task)
            startActivity(intent)
        }

    }

    fun getAllTasks() {
        val tasks = TasksDataBase.getInstance(requireContext()).getTasksDao().getAllTasks()
        adapter.setNewTasksList(tasks)
    }

    fun removeTask(task: TaskDM)
    {
        val dao = TasksDataBase.getInstance(requireContext()).getTasksDao()
        dao.deleteTask(task)
        adapter.deleteTask(task)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.onDoneClickListener=null
        adapter.onItemClickListener=null
    }

    override fun onResume() {
        super.onResume()
        getAllTasks()
    }

}
//
