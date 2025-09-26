package com.delighted2wins.souqelkhorda.core.enums

import java.util.Locale

enum class NotificationMessagesEnum(
    private val enMessage: String,
    private val arMessage: String
) {
    OFFER_SENT_PENDING("Your offer has been sent and is pending", "تم إرسال عرضك وهو قيد الانتظار"),
    OFFER_ACCEPTED("Your offer has been accepted", "تم قبول عرضك"),
    OFFER_REJECTED("Your offer has been rejected", "تم رفض عرضك"),
    OFFER_COMPLETED("Your offer has been completed", "تم إتمام عرضك"),

    RECEIVED_OFFER_PENDING("You have a new offer pending", "لديك عرض جديد قيد الانتظار"),
    RECEIVED_OFFER_ACCEPTED("Your offer has been accepted by the other party", "تم قبول العرض المقدم لك"),
    RECEIVED_OFFER_REJECTED("Your offer has been rejected by the other party", "تم رفض العرض المقدم لك"),
    RECEIVED_OFFER_COMPLETED("The offer you received has been completed", "تم إتمام العرض المقدم لك");

    fun getMessage(): String {
        val lang = Locale.getDefault().language
        return if (lang.equals("ar", ignoreCase = true)) arMessage else enMessage
    }
}