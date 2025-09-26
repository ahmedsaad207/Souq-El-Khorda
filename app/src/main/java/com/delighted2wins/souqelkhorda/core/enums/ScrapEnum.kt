package com.delighted2wins.souqelkhorda.core.enums


import java.util.Locale

enum class ScrapTypeEnum(val value: String, val arabicName: String, val engName: String) {
    ALUMINIUM("المونيوم", "المونيوم", "Aluminium"),
    IRON("حديد", "حديد", "Iron"),
    GLASS("زجاج", "زجاج", "Glass"),
    COPPER("نحاس", "نحاس", "Copper"),
    PAPER("ورق", "ورق", "Paper"),
    PLASTIC("بلاستيك", "بلاستيك", "Plastic"),
    ALL("جميع الانواع", "جميع الانواع", "All Types");

    companion object {
        fun getAllScrapTypes(): List<String> {
            val currentLanguage = Locale.getDefault().language
            return if (currentLanguage == "ar") {
                entries.map { it.arabicName }
            } else {
                entries.map { it.engName }
            }
        }

        fun getValue(scrap: String): String {
            return entries.first {
                it.arabicName.equals(scrap, ignoreCase = true) ||
                        it.engName.equals(scrap, ignoreCase = true)
            }.value
        }
    }
}
