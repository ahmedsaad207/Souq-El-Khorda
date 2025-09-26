package com.delighted2wins.souqelkhorda.features.sell.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.enums.Destination

@Composable
fun OrderDetailsSection(
    title: MutableState<String>,
    description: MutableState<String>,
    showError: MutableState<Boolean>,
    selectedDestination: MutableState<Destination>,
    price: MutableState<Int>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.order_title),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = title.value,
            onValueChange = {
                title.value = it
                if (it.isNotBlank()) showError.value = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (showError.value && title.value.isBlank()) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.5f
                    ),
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(stringResource(R.string.short_title_for_your_order),
                color = MaterialTheme.colorScheme.onSurfaceVariant) }
        )

        if (showError.value && title.value.isBlank()) {
            Text(
                text = stringResource(R.string.title_is_required),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            textFieldModifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp),
            state = description,
            label = stringResource(R.string.description_optional),
            onValueChange = { description.value = it },
            placeholder = stringResource(R.string.describe_the_order_in_detail)
        )

        if (selectedDestination.value == Destination.Market) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.suggested_price_optional),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = if (price.value == 0) "" else price.value.toString(),
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        price.value = newValue.toIntOrNull() ?: 0
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (showError.value && title.value.isBlank()) Color.Red else Color.Gray.copy(
                            alpha = 0.5f
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = { Text(stringResource(R.string.e_g_300)) }
            )
        }
    }
}