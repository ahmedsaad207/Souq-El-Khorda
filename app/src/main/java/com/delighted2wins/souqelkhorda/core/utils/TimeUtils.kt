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
                daysBetween == 1L -> "منذ يوم"
                daysBetween < 7 -> "منذ $daysBetween أيام"
                daysBetween < 30 -> {
                    val weeks = daysBetween / 7
                    if (weeks == 1L) "منذ أسبوع" else "منذ $weeks أسابيع"
                }
                daysBetween < 365 -> {
                    val months = daysBetween / 30
                    if (months == 1L) "منذ شهر" else "منذ $months أشهر"
                }
                else -> {
                    val years = daysBetween / 365
                    if (years == 1L) "منذ سنة" else "منذ $years سنوات"
                }
            }
        } else {
            when {
                daysBetween == 0L -> "Today"
                daysBetween == 1L -> "day ago"
                daysBetween < 7 -> "$daysBetween days ago"
                daysBetween < 30 -> {
                    val weeks = daysBetween / 7
                    if (weeks == 1L) "week ago" else "$weeks weeks ago"
                }
                daysBetween < 365 -> {
                    val months = daysBetween / 30
                    if (months == 1L) "month ago" else "$months months ago"
                }
                else -> {
                    val years = daysBetween / 365
                    if (years == 1L) "year ago" else "$years years ago"
                }
            }
        }

    } catch (e: Exception) {
        ""
    }
}

