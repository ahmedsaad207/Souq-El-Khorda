package com.delighted2wins.souqelkhorda.features.sell.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    state: MutableState<String>,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    keyboardOptions:KeyboardOptions = KeyboardOptions.Default
) {
    Column(
        modifier = modifier
    ) {
        if (label.isNotBlank()) {
            CustomTextFieldLabel(label)
        }
        TextField(
            value = state.value,
            onValueChange = { onValueChange(it) },
            modifier = textFieldModifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = keyboardOptions,
            placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        )
    }
}