package com.delighted2wins.souqelkhorda.core.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomSheetActionType(
    val code: Int,
    val enValue: String,
    val arValue: String,
    val color: Color,
    val warningColor: Color,
    val warningIcon: ImageVector = Icons.Outlined.Mail,
    val warnings: List<Pair<String, String>>
) {
    MAKE_OFFER(
        code = 0,
        enValue = "Make an Offer",
        arValue = "تقديم عرض",
        color = Color(0xFF4CAF50),
        warningColor = Color(0xFFB8EFBA),
        warnings = listOf(
            "The offer amount must be greater than zero. Please verify before submitting." to
                    "يجب أن يكون مبلغ العرض أكبر من صفر. يرجى التحقق قبل الإرسال.",
            "Once submitted, your offer cannot be modified unless the seller allows it." to
                    "بمجرد الإرسال، لا يمكن تعديل العرض إلا إذا سمح البائع بذلك."
        )
    ),

    UPDATE_OFFER(
        code = 1,
        enValue = "Update Offer",
        arValue = "تحديث العرض",
        color = Color(0xFF4CAF50),
        warningColor = Color(0xFFB8EFBA),
        warnings = listOf(
            "Updating the order status will notify all participants immediately." to
                    "تحديث حالة الطلب سيخطر جميع المشاركين على الفور.",
            "Make sure to select the correct status to avoid confusion." to
                    "تأكد من اختيار الحالة الصحيحة لتجنب الالتباس."
        )
    ),

    DELETE_OFFER(
        code = 2,
        enValue = "Confirm Delete",
        arValue = "تأكيد الحذف",
        color = Color(0xFFF44336),
        warningColor = Color(0xFFFFCDD2),
        warnings = listOf(
            "Deleting this order will permanently remove all related data including offers and messages." to
                    "حذف هذا الطلب سيؤدي إلى إزالة جميع البيانات المتعلقة به نهائيًا بما في ذلك العروض والرسائل.",
            "This action cannot be undone, please be certain before proceeding." to
                    "لا يمكن التراجع عن هذا الإجراء، يرجى التأكد قبل المتابعة."
        )
    ),

    CANCEL_COMPANY_ORDER(
        code = 3,
        enValue = "Cancel Order",
        arValue = "إلغاء الطلب",
        color = Color(0xFFF44336),
        warningColor = Color(0xFFFFCDD2),
        warnings = listOf(
            "Cancelling this order will notify the seller and prevent further actions on it." to
                    "إلغاء هذا الطلب سيخطر البائع ويمنع أي إجراءات إضافية عليه.",
            "You will not be able to reactivate this order once cancelled." to
                    "لن تتمكن من إعادة تنشيط هذا الطلب بعد الإلغاء."
        )
    ),

    ACCEPT_OFFER(
        code = 4,
        enValue = "Accept Offer",
        arValue = "قبول العرض",
        color = Color(0xFF4CAF50),
        warningColor = Color(0xFFB8EFBA),
        warnings = listOf(
            "Accepting this offer will notify the buyer immediately." to
                    "قبول هذا العرض سيخطر المشتري على الفور.",
            "You cannot undo this action once accepted." to
                    "لا يمكنك التراجع عن هذا الإجراء بعد القبول."
        )
    ),

    REJECT_OFFER(
        code = 5,
        enValue = "Reject Offer",
        arValue = "رفض العرض",
        color = Color(0xFFF44336),
        warningColor = Color(0xFFFFCDD2),
        warnings = listOf(
            "Rejecting this offer will notify the buyer immediately." to
                    "رفض هذا العرض سيخطر المشتري على الفور.",
            "You cannot undo this action once rejected." to
                    "لا يمكنك التراجع عن هذا الإجراء بعد الرفض."
        )
    ),

    COMPLETE_ORDER(
        code = 6,
        enValue = "Complete Order",
        arValue = "إكمال الطلب",
        color = Color(0xFF4CAF50),
        warningColor = Color(0xFFB8EFBA),
        warnings = listOf(
            "Completing this order will mark it as finished and notify all parties." to
                    "إكمال هذا الطلب سيؤدي إلى وضع علامة عليه كمكتمل وإخطار جميع الأطراف.",
            "You cannot undo this action once completed." to
                    "لا يمكنك التراجع عن هذا الإجراء بعد الإكمال."
        )
    ),

    MARK_RECEIVED(
    code = 7,
    enValue = "Mark as Received",
    arValue = "تأكيد الاستلام",
    color = Color(0xFF4CAF50),
    warningColor = Color(0xFFB8EFBA),
    warnings = listOf(
            "Marking as received will notify the seller that you have received the order." to
                    "تأكيد الاستلام سيخطر البائع أنك استلمت الطلب.",
            "You cannot undo this action once confirmed." to
                    "لا يمكنك التراجع عن هذا الإجراء بعد التأكيد."
         )
    );
}
