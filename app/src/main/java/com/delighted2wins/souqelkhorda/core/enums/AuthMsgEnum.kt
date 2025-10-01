package com.delighted2wins.souqelkhorda.core.enums

import java.util.Locale

enum class AuthMsgEnum(val message: String, val arabicMessage: String) {
    UNAUTHORIZED("Unauthorized", "غير مصرح"),
    NOINTRENET("no internet connection", "لا يوجد اتصال بالنترنت"),

    LOCATIONEMPTY("pick location, please ","يجب ان تختار الموقع الخاص بك"),
    SCRAPLISTEMPTY("You should choose some kinds","يجب ان تختار الاصناف التي تقوم بشراءها"),
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
    SIGNUPSUCCESS("Registered successfully ", "تم التسجيل بنجاح "),
    LOGINSUCCESS("Login successfully, welcome back ", "تسجيل الدخول بنجاح، مرحبًا بعودتك "),
    SIGNUPFAIL("Registration failed", "فشل التسجيل"),
    AREA("Enter valid area ","أدخل عنوان منطقة صحيح"),
    EMPTYFILDES("Please fill all fields", "يرجى ملء جميع الحقول");

    fun getMsg(): String {
        val currentLanguage = Locale.getDefault().language
        return if (currentLanguage == "ar") arabicMessage else message
    }
}