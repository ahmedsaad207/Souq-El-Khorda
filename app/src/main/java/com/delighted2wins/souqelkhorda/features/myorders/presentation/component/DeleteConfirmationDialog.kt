package com.delighted2wins.souqelkhorda.features.myorders.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DeleteConfirmationDialog(
    isRtl: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (isRtl) "تأكيد الحذف" else "Confirm Delete") },
        text = { Text(text = if (isRtl) "هل أنت متأكد من حذف الطلب؟" else "Are you sure you want to delete this order?") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color.White
                )
            ) {
                Text(text = if (isRtl) "حذف" else "Delete")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = if (isRtl) "إلغاء" else "Cancel")
            }
        }
    )
}
