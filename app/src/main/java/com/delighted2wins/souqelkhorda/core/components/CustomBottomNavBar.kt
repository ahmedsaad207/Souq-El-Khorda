package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomBottomNavBar() {
    var selectedIndex by remember { mutableStateOf(0) }

    val items = listOf("Nearest","Shop" , "Sell")
    val icons = listOf(
        Icons.Default.LocationOn,
        Icons.Default.Store,
        Icons.Default.AttachMoney
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(90.dp) // taller like the image
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color(0xFF3ED68F), Color(0xFF24B36B))
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index

                // Animated dot
                val dotSize by animateDpAsState(targetValue = if (isSelected) 6.dp else 0.dp)
                val dotAlpha by animateFloatAsState(targetValue = if (isSelected) 1f else 0f)

                Box(
                    modifier = Modifier
                        .size(80.dp) // square-ish like the highlight in image
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { selectedIndex = index },
                    contentAlignment = Alignment.Center
                ) {
                    // Background highlight only when selected
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .blur(15.dp)
                        )
                    }

                    // Foreground content
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )

                        // Dot underneath text
                        if (isSelected) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(dotSize)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                Color.White.copy(alpha = dotAlpha),
                                                Color.Transparent
                                            ),
                                            radius = 12f
                                        ),
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}
