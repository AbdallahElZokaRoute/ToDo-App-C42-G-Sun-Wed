package com.route.todoappc42gsunwed.fragments.todosList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.route.todoappc42gsunwed.databinding.ItemTaskBinding
import com.route.todoappc42gsunwed.database.model.TaskDM

class TasksAdapter(private var tasks: List<TaskDM>) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    fun setNewTasksList(tasks: List<TaskDM>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val item = tasks.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskDM) {
            binding.taskTitleTextView.text = item.title
            binding.timeTextView.text = item.date.toString()
        }

    }
}
