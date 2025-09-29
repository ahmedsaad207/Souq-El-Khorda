package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.enums.MeasurementType
import com.delighted2wins.souqelkhorda.core.enums.ScrapType
import com.delighted2wins.souqelkhorda.core.model.Scrap

@Composable
fun ScrapItemCard(
    scrap: Scrap,
    modifier: Modifier = Modifier
) {
    val scrapType = ScrapType.fromCategory(scrap.category)
    val categoryLabel = scrapType.getLabel(context = LocalContext.current)
    val measurementEnum = MeasurementType.entries.firstOrNull { it.name.equals(scrap.unit, true) }
    val measurementLabel = measurementEnum?.getLabel(LocalContext.current) ?: ""

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AsyncImage(
                model = scrap.images.firstOrNull(),
                contentDescription = "Scrap Image",
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                AssistChip(
                    onClick = {},
                    label = { Text(categoryLabel) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Category,
                            contentDescription = null,
                            tint = scrapType.tint
                        )
                    },
                    enabled = false
                )
                AssistChip(
                    onClick = {},
                    label = { Text("${scrap.amount} $measurementLabel") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Scale,
                            contentDescription = null,
                            tint = Color.Blue.copy(alpha = 0.8f),
                        )
                    },
                    enabled = false
                )
            }
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
                .fillMaxWidth(),
        ) {
            if (scrap.description.isNotEmpty()) {
                DirectionalText(
                    text = scrap.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    ),
                    maxLines = 3,
                    contentIsRtl = false
                )
            } else {
                Text(
                    text = stringResource(R.string.no_description_available),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    maxLines = 3
                )
            }
        }
    }
}
