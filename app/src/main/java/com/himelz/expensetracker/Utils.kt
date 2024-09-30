package com.himelz.expensetracker

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    fun formatToDecimal(number: Double): String {
        return String.format("%.2f", number)
    }
}