package com.delighted2wins.souqelkhorda.features.sell.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.enums.Destination

@Composable
fun OrderDestinationSection(
    selectedDestination: MutableState<Destination>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {

        TitleSection(
            text = "Order Destination",
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Direct to Company
            DestinationButton(
                modifier = Modifier.weight(1f),
                text = "Direct to Company",
                icon = Icons.Default.Apartment,
                isSelected = selectedDestination.value == Destination.Company,
                onClick = { selectedDestination.value = Destination.Company }
            )

            // To Market
            DestinationButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Store,
                text = "To Market",
                isSelected = selectedDestination.value == Destination.Market,
                onClick = { selectedDestination.value = Destination.Market }
            )

        }


    }
}


@Composable
fun DestinationButton(
    modifier: Modifier,
    icon: ImageVector,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.2f)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent)
            .padding(vertical = 16.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(32.dp),
            tint = if (isSelected) MaterialTheme.colorScheme.primary else LocalContentColor.current
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else LocalContentColor.current,
            fontWeight = FontWeight.Medium
        )
    }
}