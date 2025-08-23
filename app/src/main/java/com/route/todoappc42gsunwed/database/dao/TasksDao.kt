package com.route.todoappc42gsunwed.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.route.todoappc42gsunwed.database.model.TaskDM
import java.util.Date
//

@Dao
interface TasksDao {
    @Insert
    fun insertTask(task: TaskDM)

    @Update
    fun updateTask(task: TaskDM)

    @Delete
    fun deleteTask(task: TaskDM)

    @Query("SELECT * from tasks")
    fun getAllTasks(): List<TaskDM>

    @Query("SELECT * from tasks WHERE date = :date")
    fun getTasksByDate(date: Date): List<TaskDM>
}
