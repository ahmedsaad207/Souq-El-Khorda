package com.delighted2wins.souqelkhorda.core.utils

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@SuppressLint("NewApi")
fun getTimeAgo(dateString: String, isArabic: Boolean = true): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val scrapDate = LocalDate.parse(dateString, formatter)
        val today = LocalDate.now()

        val daysBetween = ChronoUnit.DAYS.between(scrapDate, today)

        if (isArabic) {
            when {
                daysBetween == 0L -> "اليوم"
                daysBetween == 1L -> "منذ يوم واحد"
                daysBetween < 7 -> "منذ $daysBetween أيام"
                daysBetween < 30 -> {
                    val weeks = daysBetween / 7
                    "منذ $weeks أسبوع${if (weeks > 1) "ان" else ""}"
                }
                daysBetween < 365 -> {
                    val months = daysBetween / 30
                    "منذ $months شهر${if (months > 1) "ًا" else ""}"
                }
                else -> {
                    val years = daysBetween / 365
                    "منذ $years سنة${if (years > 1) "ً" else ""}"
                }
            }
        } else {
            when {
                daysBetween == 0L -> "Today"
                daysBetween == 1L -> "1 day ago"
                daysBetween < 7 -> "$daysBetween days ago"
                daysBetween < 30 -> {
                    val weeks = daysBetween / 7
                    "$weeks week${if (weeks > 1) "s" else ""} ago"
                }
                daysBetween < 365 -> {
                    val months = daysBetween / 30
                    "$months month${if (months > 1) "s" else ""} ago"
                }
                else -> {
                    val years = daysBetween / 365
                    "$years year${if (years > 1) "s" else ""} ago"
                }
            }
        }

    } catch (e: Exception) {
        ""
    }
}

