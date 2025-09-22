package com.delighted2wins.souqelkhorda.core.utils

import java.text.SimpleDateFormat
import java.util.*

fun generateUiOrderId(orderId: String, date: Long): String {
    val shortId = if (orderId.length > 6) orderId.take(6) else orderId

    val sdf = SimpleDateFormat("ddMMyy", Locale.getDefault())
    val datePart = sdf.format(Date(date))

    return "$shortId-$datePart"
}
