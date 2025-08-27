package com.route.todoappc42gsunwed.fragments.callback

import com.route.todoappc42gsunwed.database.model.TaskDM

// 1- interface callback -  Delegate
interface OnTaskClickListener {
    fun onTaskClick(task: TaskDM, position: Int)
}
