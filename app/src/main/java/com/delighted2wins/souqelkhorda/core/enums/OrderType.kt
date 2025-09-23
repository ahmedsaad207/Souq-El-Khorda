package com.delighted2wins.souqelkhorda.core.enums

import androidx.compose.ui.graphics.Color
import java.util.Locale

enum class OrderType(
    val code: Int,
    val enValue: String,
    val arValue: String,
    val color: Color
) {
    SALE(
        code = 0,
        enValue = "Sale",
        arValue = "بيع",
        color = Color(0xFF2A62FF)
    ),
    MARKET(
        code = 1,
        enValue = "Market",
        arValue = "سوق",
        color = Color(0xFF9C27B0)
    );

    fun getLocalizedValue(): String {
        return if (Locale.getDefault().language == "ar") arValue else enValue
    }

    companion object {
        fun fromCode(code: Int): OrderType =
            entries.firstOrNull { it.code == code } ?: SALE
    }
}