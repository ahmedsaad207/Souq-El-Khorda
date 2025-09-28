package com.delighted2wins.souqelkhorda.core.utils

import android.content.Context
import com.delighted2wins.souqelkhorda.R
import java.text.SimpleDateFormat
import java.util.*

fun Long.toFormattedDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd-M-yyyy", Locale.getDefault())
    return format.format(date)
}

fun Long.toSinceString(context: Context): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> context.getString(R.string.just_now)

        minutes < 60 -> context.resources.getQuantityString(
            R.plurals.minutes_ago,
            minutes.toInt(),
            minutes
        )

        hours < 24 -> context.resources.getQuantityString(
            R.plurals.hours_ago,
            hours.toInt(),
            hours
        )

        days == 0L -> context.getString(R.string.today)

        days == 1L -> context.getString(R.string.yesterday)

        days < 7 -> context.resources.getQuantityString(
            R.plurals.days_ago,
            days.toInt(),
            days
        )

        days < 30 -> {
            val weeks = (days / 7).toInt()
            context.resources.getQuantityString(
                R.plurals.weeks_ago,
                weeks,
                weeks
            )
        }

        days < 365 -> {
            val months = (days / 30).toInt()
            context.resources.getQuantityString(
                R.plurals.months_ago,
                months,
                months
            )
        }

        else -> {
            val years = (days / 365).toInt()
            context.resources.getQuantityString(
                R.plurals.years_ago,
                years,
                years
            )
        }
    }
}


fun getTimeAgoFromMillis(context: Context, timeMillis: Long): String {
    val diff = System.currentTimeMillis() - timeMillis
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val weeks = days / 7
    val months = days / 30
    val years = days / 365

    return when {
        seconds < 60 -> context.getString(R.string.just_now)

        minutes < 60 -> context.resources.getQuantityString(
            R.plurals.minutes_ago, minutes.toInt(), minutes
        )

        hours < 24 -> context.resources.getQuantityString(
            R.plurals.hours_ago, hours.toInt(), hours
        )

        days == 1L -> context.getString(R.string.yesterday)

        days < 7 -> context.resources.getQuantityString(
            R.plurals.days_ago, days.toInt(), days
        )

        days < 30 -> context.resources.getQuantityString(
            R.plurals.weeks_ago, weeks.toInt(), weeks
        )

        days < 365 -> context.resources.getQuantityString(
            R.plurals.months_ago, months.toInt(), months
        )

        else -> context.resources.getQuantityString(
            R.plurals.years_ago, years.toInt(), years
        )
    }
}
