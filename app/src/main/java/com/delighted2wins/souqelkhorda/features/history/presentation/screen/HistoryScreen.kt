package com.delighted2wins.souqelkhorda.features.history.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showBackground = true)
@Composable
fun PreviewHistoryCard() {
    HistoryCard(
        title = "Plastic Bottles Sale",
        transactionType = "Market",
        status = "Completed",
        date = "Dec 15, 2025",
        items = listOf("15x Plastic Bottles", "8x Aluminum Cans", "2x Glass Jars"),
        expanded = true,
        onExpandToggle = {},
        onViewDetails = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHistoryScreen() {
    val dummyOrders = listOf(
        ScrapOrder(
            id = 1,
            title = "Plastic Bottles Sale",
            type = "Market",
            status = "Completed",
            dateCreated = "Dec 15, 2025",
            amount = "$12.50",
            items = listOf("15x Plastic Bottles", "8x Aluminum Cans", "2x Glass Jars")
        ),
        ScrapOrder(
            id = 2,
            title = "Cardboard Collection",
            type = "Sale",
            status = "Pending",
            dateCreated = "Dec 14, 2025",
            amount = "$8.25",
            items = listOf("12x Large Cardboard Boxes", "5x Small Paper Bags")
        ),
        ScrapOrder(
            id = 3,
            title = "Organic Waste Sale",
            type = "Market",
            status = "Completed",
            dateCreated = "Dec 13, 2025",
            amount = "$8.75",
            items = listOf("3kg Food Scraps", "2kg Garden Waste", "1kg Coffee Grounds", "0.5kg Fruit Peels")
        )
    )

    HistoryScreen(
        orders = dummyOrders,
        onBackClick = {},
        onFilterClick = {}
    )
}



@Composable
fun HistoryScreen(
    orders: List<ScrapOrder>,
    onBackClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
    ) {
        // 🔹 Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2E7D32)) // green
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Transaction History",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Clean Streets Activity",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
            IconButton(onClick = onFilterClick) {
                Icon(Icons.Default.FilterList, contentDescription = "Filter", tint = Color.White)
            }
        }

        // 🔹 Summary Row (looks like it overlaps top bar)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = (-20).dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryStat("24", "Completed", Color(0xFF4CAF50))
                SummaryStat("3", "Pending", Color(0xFFFF9800))
                SummaryStat("2", "Cancelled", Color(0xFFF44336))
            }
        }

        // 🔹 Tabs (All, Sale, Market)
        var selectedTab by remember { mutableStateOf("All") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf("All", "Sale", "Market").forEach { tab ->
                TabChip(
                    text = tab,
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab }
                )
            }
        }

        // 🔹 Orders List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp) // tiny gap below chips
        ) {
            items(orders) { order ->
                HistoryCard(
                    title = order.title,
                    transactionType = if (order.type == "sale") "Sale" else "Market",
                    status = "Completed", // Replace with real field
                    date = order.dateCreated,
                    items = order.items.map { it.toString() },
                    expanded = false,
                    onExpandToggle = {},
                    onViewDetails = { /* navigate */ }
                )
            }
        }
    }
}

@Composable
fun TabChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(50)) // pill shape
            .clickable { onClick() },
        color = if (selected) Color(0xFF2E7D32) else Color(0xFFE0E0E0), // green when active, gray when not
        tonalElevation = if (selected) 4.dp else 0.dp,
        shadowElevation = 0.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            color = if (selected) Color.White else Color.Black,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}



@Composable
fun SummaryStat(
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    outerSize: Dp = 56.dp,
    innerSize: Dp = 28.dp,
    iconSize: Dp = 12.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // outer pale circle with an inner (darker) filled circle containing the icon
        Box(
            modifier = Modifier
                .size(outerSize),
            contentAlignment = Alignment.Center
        ) {
            // pale outer circle
            Box(
                modifier = Modifier
                    .size(outerSize)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f))
            )

            // inner filled circle
            Box(
                modifier = Modifier
                    .size(innerSize)
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                val icon = when (label) {
                    "Completed" -> Icons.Default.Check
                    "Pending" -> Icons.Default.AccessTime
                    "Cancelled" -> Icons.Default.Close
                    else -> Icons.Default.Info
                }

                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White,      // white icon on colored inner circle (good contrast)
                    modifier = Modifier.size(iconSize)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        )
    }
}





@Composable
fun HistoryCard(
    title: String = "Plastic Bottle Sale",
    transactionType: String = "Sale", // "Sale" or "Market"
    status: String = "Pending",       // "Completed", "Pending", "Cancelled"
    date: String = "Dec 15, 2025",
    description: String = "Collected from recycling center.",
    items: List<String>,
    expanded: Boolean,
    onExpandToggle: () -> Unit,
    onViewDetails: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(expanded) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // 🔹 Row 1: Title + Chips + Expand Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f) // title takes remaining space
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    StatusChip(status = status)
                    TypeChip(type = transactionType)
                }

                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
            }

            // 🔹 Row 2: Description
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
            )

            // 🔹 Row 3: Date
            Text(
                text = "Date: $date",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )

            // 🔹 Expanded Content
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ) {
                    // Inner card for items
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Items:", fontWeight = FontWeight.Bold)

                            items.forEach {
                                Text("• $it", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onViewDetails,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("View Details")
                    }
                }
            }
        }
    }
}





@Composable
fun StatusChip(status: String) {
    val (bg, textColor) = when (status) {
        "Completed" -> Color(0xFFE6F4EA) to Color(0xFF2E7D32)
        "Pending" -> Color(0xFFFFF4E6) to Color(0xFFED6C02)
        "Cancelled" -> Color(0xFFFDECEA) to Color(0xFFC62828)
        else -> Color.LightGray to Color.DarkGray
    }

    Box(
        modifier = Modifier
            .background(bg, RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(status, color = textColor, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun TypeChip(type: String) {
    val (bg, textColor) = when (type) {
        "Sale" -> Color(0xFFE3F2FD) to Color(0xFF1976D2)
        "Market" -> Color(0xFFF3E5F5) to Color(0xFF7B1FA2)
        else -> Color.LightGray to Color.DarkGray
    }

    Box(
        modifier = Modifier
            .background(bg, RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(type, color = textColor, style = MaterialTheme.typography.bodySmall)
    }
}

data class ScrapOrder(
    val id: Int,
    val title: String,
    val type: String, // "Sale" or "Market"
    val status: String, // "Completed", "Pending", "Cancelled"
    val dateCreated: String,
    val amount: String,
    val items: List<String>
)
