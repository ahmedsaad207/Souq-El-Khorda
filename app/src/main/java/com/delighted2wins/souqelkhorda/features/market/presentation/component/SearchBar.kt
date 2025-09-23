package com.delighted2wins.souqelkhorda.features.market.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.app.theme.Til

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isRtl: Boolean
) {
    val roundedShape = RoundedCornerShape(20.dp)

    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = if (isRtl) "ابحث في الخردة..." else "Search in scraps...",
                color = Color.Gray,
                style = if (isRtl) TextStyle(textAlign = TextAlign.End) else TextStyle(textAlign = TextAlign.Start)
            )
        },
        leadingIcon = if (!isRtl) {
            {
                Icon(Icons.Default.Search, contentDescription = null, tint = Til)
            }
        } else if (query.isNotEmpty()) {
            {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear", tint = Til)
                }
            }
        } else null,
        trailingIcon = if (isRtl) {
            if (query.isNotEmpty()) {
                {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear", tint = Til)
                    }
                }
            } else {
                {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Til)
                }
            }
        } else if (query.isNotEmpty()) {
            {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear", tint = Til)
                }
            }
        } else null,
        singleLine = true,
        shape = roundedShape,
        modifier = modifier
            .height(56.dp)
            .border(width = 2.dp, color = Til, shape = roundedShape),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Til,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreviewLTR() {
    SearchBar(query = "", onQueryChange = {}, isRtl = false)
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreviewRTL() {
    SearchBar(query = "", onQueryChange = {}, isRtl = true)
}