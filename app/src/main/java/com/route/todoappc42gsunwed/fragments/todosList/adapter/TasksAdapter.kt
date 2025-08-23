package com.route.todoappc42gsunwed.fragments.todosList.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.route.todoappc42gsunwed.R
import com.route.todoappc42gsunwed.databinding.ItemTaskBinding
import com.route.todoappc42gsunwed.database.model.TaskDM

class TasksAdapter(private var tasks: MutableList<TaskDM>,private val onDeleteClick:(TaskDM)->Unit) :
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

    @SuppressLint("NotifyDataSetChanged")
    fun setNewTasksList(tasks: List<TaskDM>) {
        this.tasks = tasks as MutableList<TaskDM>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val item = tasks.get(position)
        holder.bind(item)
        holder.isDoneStatus(item.isDone == true)
        holder.binding.leftView.setOnClickListener {
            onDeleteClick(item)
        }
        onDoneClickListener?.let {
            holder.binding.checkImageView.setOnClickListener {
                if (item.isDone != true) {
                    item.isDone = true
                    holder.isDoneStatus(true)
                    onDoneClickListener?.onClick(position, item)
                }
            }
        }
        onItemClickListener?.let{
            holder.binding.dragView.setOnClickListener {
                onItemClickListener?.onClick(position,item)
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


    class TaskViewHolder(internal val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskDM) {
            binding.taskTitleTextView.text = item.title
            binding.timeTextView.text = item.date.toString()
        }

        fun isDoneStatus(isDone: Boolean)
        {
            if(isDone == true)
            {
                binding.checkImageView.setImageResource(R.drawable.done_view)
                binding.taskTitleTextView.setTextColor(Color.GREEN)
                binding.verticalView.setBackgroundColor(Color.GREEN)
            }
            else
            {
                binding.checkImageView.setImageResource(R.drawable.ic_check)
                binding.taskTitleTextView.setTextColor(Color.BLUE)
                binding.verticalView.setBackgroundColor(Color.BLUE)
            }
        }


    }
    fun deleteTask(task: TaskDM)
    {
        val pos=tasks.indexOf(task)
        if(pos!=-1)
        {
            tasks.removeAt(pos)
            notifyItemRemoved(pos)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun createNewList(newTask: List<TaskDM>)
    {
        tasks.clear()
        tasks.addAll(newTask)
        notifyDataSetChanged()
    }
    var onDoneClickListener:onTaskClickListener?=null
    var onItemClickListener:onTaskClickListener?=null
    fun interface onTaskClickListener{
         fun onClick(position: Int,taskDM: TaskDM)
    }

}
//
