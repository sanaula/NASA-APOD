package com.example.wltest

import android.icu.text.SimpleDateFormat
import java.util.*

private const val FORMAT = "yyyy-MM-dd"

object CustomDateUtils {

    fun getTime(string: String?) : Long {
        return try {
            val dateFormat = SimpleDateFormat(FORMAT, Locale.getDefault())
            val date = dateFormat.parse(string)
            date?.time?:0
        } catch (ex: Exception) {
            0
        }
    }

    fun getDateFromTime(time: Long): Long {
        val calendar = Calendar.getInstance().apply {
            setTime(Date(time))
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.toInstant().toEpochMilli()
    }
}
