package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavBackStack
import com.delighted2wins.souqelkhorda.navigation.navItems

@Composable
fun AppBottomNavBar(backStack: NavBackStack) {
    val selectedItem = backStack.lastOrNull()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(100.dp)
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color(0xFF32C781), Color(0xFF24B36B))
                    ),
                    shape = RoundedCornerShape(20.dp)
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { navItem ->
                val isSelected = selectedItem == navItem.key

                val dotSize by animateDpAsState(targetValue = if (isSelected) 6.dp else 0.dp)
                val dotAlpha by animateFloatAsState(targetValue = if (isSelected) 1f else 0f)

                val scale by animateFloatAsState(if (isSelected) 1.2f else 1f)
                val offsetY by animateDpAsState(if (isSelected) (-8).dp else 0.dp)

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                        .offset(y = offsetY)
                        .scale(scale)
                        .padding(start = 4.dp, end = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            if(!isSelected) {
                                backStack.set(element = navItem.key, index = 0)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .blur(15.dp)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = stringResource(id = navItem.labelRes),
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text =  stringResource(id = navItem.labelRes),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )

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
