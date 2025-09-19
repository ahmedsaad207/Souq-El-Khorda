package com.delighted2wins.souqelkhorda.core.enums

import java.util.Locale

enum class LanguageEnum (val code: String, val value: String, val arValue: String) {
    ENGLISH("en", "English", "English"),
    ARABIC("ar", "العربية", "العربية"),
    DEFAULT("Default", "Default", "لغة النظام");

    companion object {
        fun geCodeByValue(value: String): String {
            val lang = Locale.getDefault().language
            return when (lang) {
                "ar" -> LanguageEnum.entries.find { it.arValue == value }?.code
                    ?: ENGLISH.code

                else -> LanguageEnum.entries.find { it.value == value }?.code
                    ?: ENGLISH.code
            }

        }

        fun getValue(code: String): String {
            val lang = Locale.getDefault().language
            return when (lang) {
                "ar" -> LanguageEnum.entries.find { it.code==code }?.arValue?: ENGLISH.arValue
                else -> LanguageEnum.entries.find { it.code==code }?.value?: ENGLISH.value
            }
        }
    }
}