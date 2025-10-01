package com.delighted2wins.souqelkhorda.core.utils

import androidx.compose.ui.unit.LayoutDirection

fun isArabic(text: String): Boolean = text.any { it in '\u0600'..'\u06FF' }

fun detectLayoutDirection(text: String): LayoutDirection {
    val firstChar = text.firstOrNull()
    return if (firstChar != null && firstChar in '\u0600'..'\u06FF') {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }
}