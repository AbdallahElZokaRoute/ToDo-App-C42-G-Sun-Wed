package com.route.todoappc42gsunwed.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Tasks")
data class TaskDM(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val date: Date? = null,
    val isDone: Boolean? = false,
)

// CRUD (Create Read Update Delete)


//Local Storage of Mobile Device ->
//
//
//              Files     -> CRUD (Create Read Update Delete)
//                    Files  X
//                  Files Manipulation
//                  Files  <->

// Data Access Object -> DAO ->
//
//                                   // Compile-Time Verification
// SQLite    -> Boilerplate          // Avoid Boilerplate code  //

//
//

