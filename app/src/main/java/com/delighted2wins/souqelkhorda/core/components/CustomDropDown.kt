package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp

@Composable
fun <T : Enum<T>> CustomDropDown(
    modifier: Modifier = Modifier,
    options: Array<T>,
    onOptionSelected: (T) -> Unit,
    labelMapper: (T) -> String,
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
                    width = 1.dp, color = Color.Gray.copy(0.5f), shape = RoundedCornerShape(12.dp)
                )
                .clickable { expanded = !expanded }
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .onGloballyPositioned { coordinates ->
                    rowWidth = coordinates.size.width
                }
        ) {
            Text(
                text = labelMapper(selectedOption)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) {rowWidth.toDp() + 24.dp})
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .border(
                    1.dp,
                    color = Color.Gray.copy(0.5f),
                    shape = RoundedCornerShape(12.dp)
                )   ,
            containerColor = Color.Transparent,
            shadowElevation = 0.dp,
        ) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(labelMapper(option)) }, onClick = {
                    onOptionSelected(option)
                    expanded = false
                })
            }
        }
    }
}