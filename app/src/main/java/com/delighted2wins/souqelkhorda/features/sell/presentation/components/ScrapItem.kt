package com.delighted2wins.souqelkhorda.features.sell.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.model.Scrap

@Composable
fun ScrapItem(
    scrap: Scrap,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Icon(
            imageVector = Icons.Default.Place,
            contentDescription = scrap.category,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = scrap.category,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = scrap.amount.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = scrap.unit,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

        }
        Row {
            IconButton(
                onClick = onEdit
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.Gray
                )
            }
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }

    }
}