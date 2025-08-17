package com.route.todoappc42gsunwed.database.typeConverter

import androidx.room.TypeConverter
import java.util.Date

class DateTypeConverter {
    @TypeConverter
    fun convertDateToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun convertLongToDate(date: Long): Date {
        return Date(date)
    }
}