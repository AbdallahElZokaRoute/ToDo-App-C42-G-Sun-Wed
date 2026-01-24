package model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

data class CalderModel(
    val dayOfWeek: String,
    val day: String,
    val month: String,
val year: Int,
)

@RequiresApi(Build.VERSION_CODES.O)
fun getDaysOfMonth(year: Int, month: Int): List<CalderModel> {
    val yearMonth = YearMonth.of(year, month)
   // val monthName = yearMonth.month.getDisplayName(TextStyle.FULL, Locale("en"))
    val monthName = yearMonth.monthValue.toString()
    val daysInMonth = yearMonth.lengthOfMonth()
    return (1..daysInMonth).map { day ->
        val date = LocalDate.of(year, month, day)

        CalderModel(
            day = day.toString().padStart(2, '0'),
            month = monthName,
            dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("en")),
            year = year,

        )
    }

}