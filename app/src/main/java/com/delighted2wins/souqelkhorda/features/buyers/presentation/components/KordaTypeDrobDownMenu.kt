package com.delighted2wins.souqelkhorda.features.buyers.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun KordaTypeDrobDownMenu(
    options: List<String>,
    selectedOptions: List<String>,
    onSelectionChange: (List<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val colors = MaterialTheme.colorScheme
    Box {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier
                .width((screenWidth * 0.85).dp)
                .padding(6.dp),
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
        ) {
            Text(
                text = if (selectedOptions.isEmpty()) stringResource(R.string.select_items) else selectedOptions.joinToString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = colors.onSurface

            )
        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                val isSelected = option in selectedOptions
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(option)
                        }
                    },
                    onClick = {
                        val newSelection = if (isSelected) {
                            selectedOptions - option
                        } else {
                            selectedOptions + option
                        }
                        onSelectionChange(newSelection)
                    }
                )
            }
        }
    }
}
