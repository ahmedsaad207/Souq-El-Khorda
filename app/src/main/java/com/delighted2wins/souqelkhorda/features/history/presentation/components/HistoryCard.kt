package com.delighted2wins.souqelkhorda.features.history.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.app.theme.AppTypography
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.model.Scrap


@Composable
fun HistoryCard(
    title: String,
    subtitle: String,
    status: OrderStatus,
    date: String,
    items: List<Scrap>,
    expanded: Boolean,
    onViewDetails: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(expanded) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                StatusChip(status = status)
            }

            Text(
                text = subtitle,
                style = AppTypography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )

            Text(
                text = date,
                style = AppTypography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                border = CardDefaults.outlinedCardBorder(),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isExpanded = !isExpanded }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.order_items, items.size),
                            style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null
                        )
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column {
                            Divider(color = Color.LightGray, thickness = 0.5.dp)
                            items.forEach { scrap ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CategoryIcon(scrap.category)
                                    Spacer(Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = scrap.category,
                                            style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                        )
                                        Text(
                                            text = "${scrap.amount} ${scrap.unit}" +
                                                    if (scrap.description.isNotBlank()) " – ${scrap.description}" else "",
                                            style = AppTypography.bodySmall,
                                            color = Color.Gray
                                        )
                                    }
                                }
                                Divider(color = Color.LightGray, thickness = 0.5.dp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onViewDetails,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Details", style = AppTypography.bodyMedium)
            }
        }
    }
}


data class CategoryStyle(val icon: Int, val tint: Color)

fun getCategoryStyle(category: String): CategoryStyle {
    return when (category.uppercase()) {
        "PLASTIC" -> CategoryStyle(R.drawable.plastic, Color(0xFFFE7E0F))
        "GLASS" -> CategoryStyle(R.drawable.glass, Color(0xFFFF1111))
        "ALUMINIUM" -> CategoryStyle(R.drawable.aluminum, Color(0xFF00B259))
        "PAPER" -> CategoryStyle(R.drawable.paper, Color(0xFF2A62FF))
        else -> CategoryStyle(R.drawable.custom, Color.Gray)
    }
}

@Composable
fun CategoryIcon(category: String, size: Dp = 40.dp) {
    val style = getCategoryStyle(category)

    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = style.tint.copy(alpha = 0.15f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(style.icon),
            contentDescription = category,
            tint = style.tint,
            modifier = Modifier.size(size * 0.6f)
        )
    }
}



@Composable
fun StatusChip(status: OrderStatus) {
    val (bg, textColor) = when (status) {
        OrderStatus.PENDING -> OrderStatus.PENDING.color to Color(0xFFE6F4EA)
        OrderStatus.COMPLETED -> OrderStatus.COMPLETED.color to Color(0xFFFFF4E6)
        OrderStatus.CANCELLED -> OrderStatus.CANCELLED.color to Color(0xFFFDECEA)
    }

    Box(
        modifier = Modifier
            .background(bg, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(status.getLocalizedValue(), color = textColor, style = AppTypography.bodyMedium)
    }
}

