package com.delighted2wins.souqelkhorda.core.extensions

import java.util.Locale

fun String.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
fun String.isPhoneNumber(): Boolean {
    return this.matches( "^01[0-9]{9}$".toRegex())
}

fun String.isUserName(): Boolean {
    return this.length >= 3
}

fun String.isPassword(): Boolean {
    val regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*(),.?\":{}|<>]).{6,}$".toRegex()
    return this.matches(regex)
}
fun String.isAddress(): Boolean {
    return this.length >= 15
}

fun String.convertNumbersToArabic(): String {
    return if (Locale.getDefault().language == "ar") {
        this.map { char ->
            if (char in '0'..'9') {
                ('\u0660' + (char - '0')).toChar()
            } else {
                char
            }
        }.joinToString("")
    } else {
        this
    }
}