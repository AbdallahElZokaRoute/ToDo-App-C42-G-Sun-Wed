package com.route.todoappc42gsunwed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.route.todoappc42gsunwed.database.dao.TasksDao
import com.route.todoappc42gsunwed.database.model.TaskDM
import com.route.todoappc42gsunwed.database.typeConverter.DateTypeConverter

@TypeConverters(value = [DateTypeConverter::class])
@Database(entities = [TaskDM::class], version = 1) // Schema
abstract class TasksDataBase : RoomDatabase() {
    abstract fun getTasksDao(): TasksDao

    companion object {
        private var INSTANCE: TasksDataBase? = null
        private val DATABASE_NAME = "Tasks Database"

        fun getInstance(context: Context): TasksDataBase {
            if (INSTANCE == null)
                INSTANCE =
                    Room.databaseBuilder(context, TasksDataBase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()//   Main Thread -> UI Thread (Navigation and Animation and Visibility of Views)      X     ->  Kotlin Coroutines
                        .fallbackToDestructiveMigration(true) // Schema of the database
                        .build()
            return INSTANCE!!
        }
    }
}
//