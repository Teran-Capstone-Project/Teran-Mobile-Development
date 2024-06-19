package com.example.teran.data.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateHelper {
    fun getCurrentDate(): String {
        val locale = Locale("id", "ID")
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", locale)
        val date = Date()
        return dateFormat.format(date)
    }

    fun formatIsoToIndonesianDate(isoDate: String): String {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date = isoFormat.parse(isoDate)!!
        val indonesiaFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        return indonesiaFormat.format(date)
    }

    fun formatIsoToIndonesianTime(isoDate: String): String {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date = isoFormat.parse(isoDate)!!
        val indonesiaFormat = SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", Locale("id", "ID"))
        return indonesiaFormat.format(date)
    }
}
