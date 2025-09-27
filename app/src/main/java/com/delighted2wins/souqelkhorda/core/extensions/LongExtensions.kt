package com.delighted2wins.souqelkhorda.core.extensions

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.delighted2wins.souqelkhorda.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


fun Long.toFormattedDate(pattern: String = "MMM dd, yyyy"): String {
    return try {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.format(Date(this))
    } catch (e: Exception) {
        ""
    }
}


fun Long.toRelativeTime(context: Context): String {
    val now = System.currentTimeMillis()
    val timeMillis = if (this in 1..999_999_999_999L) this * 1000L else this
    val safeTime = if (timeMillis > now) now else timeMillis
    val diff = now - safeTime

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) ->
            context.getString(R.string.just_now)
        diff < TimeUnit.HOURS.toMillis(1) ->
            context.resources.getQuantityString(R.plurals.minutes_ago, minutes.toInt(), minutes)
        diff < TimeUnit.DAYS.toMillis(1) ->
            context.resources.getQuantityString(R.plurals.hours_ago, hours.toInt(), hours)
        diff < TimeUnit.DAYS.toMillis(2) ->
            context.getString(R.string.yesterday)
        diff < TimeUnit.DAYS.toMillis(7) ->
            context.resources.getQuantityString(R.plurals.days_ago, days.toInt(), days)
        diff < TimeUnit.DAYS.toMillis(30) -> {
            val weeks = (days / 7).toInt()
            context.resources.getQuantityString(R.plurals.weeks_ago, weeks, weeks)
        }
        diff < TimeUnit.DAYS.toMillis(365) -> {
            val months = (days / 30).toInt()
            context.resources.getQuantityString(R.plurals.months_ago, months, months)
        }
        else -> {
            val years = (days / 365).toInt()
            context.resources.getQuantityString(R.plurals.years_ago, years, years)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun Long.toFormattedDateTime(pattern: String = "MMM dd, yyyy"): String {
    return try {
        val instant = Instant.ofEpochMilli(this)
        val formatter = DateTimeFormatter.ofPattern(pattern)
            .withZone(ZoneId.systemDefault())
        formatter.format(instant)
    } catch (e: Exception) {
        ""
    }
}
