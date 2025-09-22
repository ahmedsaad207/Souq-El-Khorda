package com.delighted2wins.souqelkhorda.core.enums

import androidx.compose.ui.graphics.Color
import java.util.Locale

enum class OrderStatus(
    val code: Int,
    val enValue: String,
    val arValue: String,
    val color: Color
) {
    PENDING(
        code = 0,
        enValue = "Pending",
        arValue = "قيد الانتظار",
        color = Color(0xFFFE7E0F)
    ),
    CANCELLED(
        code = 1,
        enValue = "Cancelled",
        arValue = "ملغاة",
        color = Color(0xFFFF1111)
    ),
    COMPLETED(
        code = 2,
        enValue = "Completed",
        arValue = "مكتملة",
        color = Color(0xFF00B259)
    );

    fun getLocalizedValue(): String {
        return if (Locale.getDefault().language == "ar") arValue else enValue
    }

    companion object {
        fun fromCode(code: Int): OrderStatus =
            entries.firstOrNull { it.code == code } ?: PENDING
    }
}