package com.delighted2wins.souqelkhorda.core.enums

import java.util.Locale

enum class GovernorateEnum(val value: String, val arabicName: String, val engName: String) {
    CAIRO("القاهرة", "القاهرة", "Cairo"),
    GIZA("الجيزة", "الجيزة", "Giza"),
    ALEXANDRIA("الإسكندرية", "الإسكندرية", "Alexandria"),
    DAKAHLIYA("الدقهلية", "الدقهلية", "Dakahlia"),
    RED_SEA("البحر الأحمر", "البحر الأحمر", "Red Sea"),
    BEHEIRA("البحيرة", "البحيرة", "Beheira"),
    FAYOUM("الفيوم", "الفيوم", "Fayoum"),
    GHARBIA("الغربية", "الغربية", "Gharbia"),
    ISMAILIA("الإسماعيلية", "الإسماعيلية", "Ismailia"),
    MENOFIA("المنوفية", "المنوفية", "Monufia"),
    MINYA("المنيا", "المنيا", "Minya"),
    QALIUBIA("القليوبية", "القليوبية", "Qalyubia"),
    NEW_VALLEY("الوادي الجديد", "الوادي الجديد", "New Valley"),
    SUEZ("السويس", "السويس", "Suez"),
    ASWAN("أسوان", "أسوان", "Aswan"),
    ASSIUT("أسيوط", "أسيوط", "Assiut"),
    BANI_SUEF("بني سويف", "بني سويف", "Beni Suef"),
    PORT_SAID("بورسعيد", "بورسعيد", "Port Said"),
    DAMIETTA("دمياط", "دمياط", "Damietta"),
    SHARQIA("الشرقية", "الشرقية", "Sharqia"),
    SOUTH_SINAI("جنوب سيناء", "جنوب سيناء", "South Sinai"),
    KAFR_EL_SHEIKH("كفر الشيخ", "كفر الشيخ", "Kafr El Sheikh"),
    MATROUH("مطروح", "مطروح", "Matrouh"),
    LUXOR("الأقصر", "الأقصر", "Luxor"),
    QENA("قنا", "قنا", "Qena"),
    NORTH_SINAI("شمال سيناء", "شمال سيناء", "North Sinai"),
    SOHAG("سوهاج", "سوهاج", "Sohag");

    companion object {
        fun getAllGovernorate(): List<String> {
            val currentLanguage = Locale.getDefault().language
            return if (currentLanguage == "ar") {
                GovernorateEnum.entries.map { it.arabicName }
            } else {
                GovernorateEnum.entries.map { it.engName }
            }
        }
        fun getValue(governorate: String): String {
            return GovernorateEnum.entries.first {
                it.arabicName.equals(governorate, ignoreCase = true) ||
                        it.engName.equals(governorate, ignoreCase = true)
            }.value
        }
    }

}
