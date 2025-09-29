package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 6.dp,
        title = { Text(text = title) },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        confirmButton = {
            TextButton(onConfirm) {
                Text(text = confirmLabel,style = MaterialTheme.typography.labelLarge)
            }
        },
        dismissButton = {
            TextButton(onDismiss,colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )) {
                Text(text = stringResource(R.string.cancel), style = MaterialTheme.typography.labelLarge)
            }
        }
    )
}
