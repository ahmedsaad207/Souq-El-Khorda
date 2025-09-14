package com.delighted2wins.souqelkhorda.core.utils

fun isArabic(text: String): Boolean = text.any { it in '\u0600'..'\u06FF' }

