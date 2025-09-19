package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtonsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            onClick = { /* Handle message action */ },
            modifier = Modifier.weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Message,
                    contentDescription = "Message"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Message")
            }
        }
        Button(
            onClick = { /* Handle make offer action */ },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor =  MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Make Offer")
        }
    }
}