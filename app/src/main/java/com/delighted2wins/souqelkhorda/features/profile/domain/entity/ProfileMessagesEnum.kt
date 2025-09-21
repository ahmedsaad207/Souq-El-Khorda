package com.delighted2wins.souqelkhorda.features.profile.domain.entity

import java.util.Locale

enum class ProfileMessagesEnum(
    val enMessage: String,
    val arMessage: String
) {

    NETWORK("Network error", "خطأ في الشبكة"),
    UNAUTHORIZED("Unauthorized action", "غير مصرح"),
    USER_NOT_FOUND("User not found", "المستخدم غير موجود"),
    UNKNOWN("Unknown error", "خطأ غير معروف"),

    NAME_INVALID("Enter a valid name", "أدخل اسمًا صالحًا"),
    EMAIL_INVALID("Enter a valid email", "أدخل بريدًا إلكترونيًا صالحًا"),
    PHONE_INVALID("Enter a valid phone number", "أدخل رقم هاتف صالح"),
    GOVERNORATE_INVALID("Select a valid governorate", "اختر محافظة صالحة"),
    ADDRESS_INVALID("Enter a valid address (at least 15 characters)", "أدخل عنوانًا صالحًا (15 حرفًا على الأقل)"),
    IMAGE_INVALID("Invalid image selected", "الصورة المحددة غير صالحة");


    fun getMsg(): String {
        val currentLanguage = Locale.getDefault().language
        return if (currentLanguage == "ar") arMessage else enMessage
    }
}