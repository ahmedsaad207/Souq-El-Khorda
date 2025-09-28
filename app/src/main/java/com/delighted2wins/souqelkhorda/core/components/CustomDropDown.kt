package com.delighted2wins.souqelkhorda.core.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@SuppressLint("SupportAnnotationUsage")
@Composable
fun <T : Enum<T>> CustomDropDown(
    modifier: Modifier = Modifier,
    options: Array<T>,
    onOptionSelected: (T) -> Unit,
    labelMapper: (T) -> String,
    iconTintMapper: (T) -> Color = { Color.Unspecified },
    @DrawableRes iconResMapper: (T) -> Int = { 0 },
    selectedOption: T
) {

    var expanded by remember { mutableStateOf(false) }
    var rowWidth by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(0.5f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { expanded = !expanded }
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .onGloballyPositioned { coordinates ->
                    rowWidth = coordinates.size.width
                }
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                if (iconResMapper(selectedOption) != 0) {

                    Icon(
                        painter = painterResource(iconResMapper(selectedOption)),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        tint = iconTintMapper(selectedOption)
                    )
                    Spacer(Modifier.width(12.dp))
                }
                Text(
                    text = labelMapper(selectedOption),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { rowWidth.toDp() + 24.dp })
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(0.5f),
                    shape = RoundedCornerShape(12.dp)
                ),
            containerColor = Color.Transparent,
            shadowElevation = 4.dp,
        ) {
            options.forEach { option ->
                val isSelected = option == selectedOption
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary.copy(0.1f)
                            else Color.Transparent,
                            shape = RoundedCornerShape(8.dp),
                        ),
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (iconResMapper(option) != 0) {
                                Icon(
                                    painter = painterResource(iconResMapper(option)),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp),
                                    tint = iconTintMapper(option)
                                )
                                Spacer(Modifier.width(12.dp))
                            }
                            Text(
                                labelMapper(option),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }, onClick = {
                        onOptionSelected(option)
                        expanded = false
                    })
            }
        }
    }
}