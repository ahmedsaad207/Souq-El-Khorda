package com.delighted2wins.souqelkhorda.features.market.presentation.component.OrderDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.DirectionalText

@Composable
fun ExpandableDescription(
    description: String,
    collapsedMaxLines: Int = 3,
    contentIsRtl: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        DirectionalText(
            text = description,
            contentIsRtl = contentIsRtl,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            maxLines = if (expanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        DirectionalText(
            text = if (expanded) {
                if (contentIsRtl) "عرض أقل" else "Read Less"
            } else {
                if (contentIsRtl) "المزيد" else "Read More"
            },
            contentIsRtl = contentIsRtl,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { expanded = !expanded }
        )

    }
}
