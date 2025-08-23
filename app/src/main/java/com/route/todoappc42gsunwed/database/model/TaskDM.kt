package com.route.todoappc42gsunwed.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date
@Parcelize
@Entity(tableName = "Tasks")
data class TaskDM(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var date: Date? = null,
    var isDone: Boolean? = false,
):Parcelable

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

