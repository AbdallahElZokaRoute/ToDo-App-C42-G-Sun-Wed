package com.route.todoappc42gsunwed.fragments.todosList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.route.todoappc42gsunwed.R
import com.route.todoappc42gsunwed.databinding.ItemTaskBinding
import com.route.todoappc42gsunwed.database.model.TaskDM
import com.route.todoappc42gsunwed.fragments.callback.OnTaskClickListener

class TasksAdapter(private var tasks: MutableList<TaskDM>) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    // 2-   Assignment -> Settings Fragment -> Edit Task Activity
    //                  AppCompatDelegate
    // Runtime Permissions <->  Google Maps  -> Jetpack Compose
    var onTaskClickListener: OnTaskClickListener? = null
    var onCheckClickListener: OnTaskClickListener? = null
    var onDeleteClickListener: OnTaskClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    fun setNewTasksList(tasks: MutableList<TaskDM>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val item = tasks.get(position)
        holder.bind(item)
        //  3-
        holder.binding.checkImageView.setOnClickListener {
            onCheckClickListener?.onTaskClick(item, position)
        }
        holder.binding.taskCardView.setOnClickListener {
            onTaskClickListener?.onTaskClick(item, position)
        }
        holder.binding.deleteImageView.setOnClickListener {
            onDeleteClickListener?.onTaskClick(item, position)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun updateTaskState(position: Int) {
        val newTask = tasks.get(position).copy(isDone = true)
        tasks.set(position, element = newTask)
        notifyItemChanged(position)
    }

    fun deleteTaskState(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskDM) {
            binding.taskTitleTextView.text = item.title
            binding.timeTextView.text = item.date.toString()
            if (item.isDone == true) {
                val greenColor = ResourcesCompat.getColor(
                    binding.root.resources,
                    R.color.green,
                    null
                )
                binding.verticalView.setBackgroundColor(greenColor)
                binding.taskTitleTextView.setTextColor(greenColor)
                binding.checkImageView.visibility = View.GONE
                binding.doneTextView.visibility = View.VISIBLE
            } else {
                val blueColor = ResourcesCompat.getColor(
                    binding.root.resources,
                    R.color.blue,
                    null
                )
                binding.verticalView.setBackgroundColor(blueColor)
                binding.taskTitleTextView.setTextColor(blueColor)
                binding.checkImageView.visibility = View.VISIBLE
                binding.doneTextView.visibility = View.GONE
            }
        }

    }
}
