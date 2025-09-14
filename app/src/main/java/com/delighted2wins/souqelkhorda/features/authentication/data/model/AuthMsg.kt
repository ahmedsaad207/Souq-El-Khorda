package com.delighted2wins.souqelkhorda.features.authentication.data.model

import java.util.Locale

enum class AuthMsg(val message: String, val arabicMessage: String) {
    NETWORK("Network error", "خطأ في الشبكة"),
    UNAUTHORIZED("Unauthorized", "غير مصرح"),
    USERNOTFOUND("User not found", "المستخدم غير موجود"),
    USERNAMEVALIDATE("Enter valid name", "أدخل اسمًا صالحًا"),
    EMAILVALIDATE("Enter valid email", "أدخل بريدًا إلكترونيًا صالحًا"),
    PASSWORDVALIDATE(
        "Password must be at least 6 characters ...",
        "يجب أن تكون كلمة المرور 6 أحرف على الأقل ..."
    ),
    PASSWORDCONFIRMATION("Password confirmation not match", " تأكيد كلمة المرور غير متطابق"),
    PHONEVALIDATE("Enter valid phone number", "أدخل رقم هاتف صالح"),
    GOVERNORATEVALIDATE("Select your governorate", "اختر محافظتك"),
    ADDRESSVALIDATE(
        "Enter valid address, must be at least 15 characters",
        "أدخل عنوانًا صالحًا، يجب أن يكون 15 حرفًا على الأقل"
    ),
    SIGNUPSUCCESS("Registered successfully, welcome ", "تم التسجيل بنجاح، مرحبًا "),
    LOGINSUCCESS("Login successfully, welcome back ", "تسجيل الدخول بنجاح، مرحبًا بعودتك "),
    SIGNUPFAIL("Registration failed", "فشل التسجيل"),
    UNKNOWN("Unknown error", "خطأ غير معروف");

    fun getMsg(): String {
        val currentLanguage = Locale.getDefault().language
        return if (currentLanguage == "ar") arabicMessage else message
    }
}


