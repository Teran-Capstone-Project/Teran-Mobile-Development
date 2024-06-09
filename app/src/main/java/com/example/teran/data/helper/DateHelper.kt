package com.example.teran.data.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {
    fun getCurrentDate(): String {
        val locale = Locale("id", "ID") // Indonesian locale for proper day and month names
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", locale)
        val date = Date()
        return dateFormat.format(date)
    }
}