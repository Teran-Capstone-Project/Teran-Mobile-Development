package com.example.teran.data.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {
    fun getCurrentDate(): String {
        val locale = Locale("id", "ID")
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", locale)
        val date = Date()
        return dateFormat.format(date)
    }

    fun formatIsoToIndonesianDate(isoDate: String): String {
        // Format dari tanggal ISO 8601
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")
        // Parse tanggal dari format ISO 8601
        val date: Date = isoFormat.parse(isoDate)!!
        // Format baru untuk tanggal dalam bahasa Indonesia
        val indonesiaFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        // Format tanggal dalam bahasa Indonesia
        return indonesiaFormat.format(date)
    }
}