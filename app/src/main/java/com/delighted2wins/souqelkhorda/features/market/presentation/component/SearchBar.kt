package com.delighted2wins.souqelkhorda.features.market.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.app.theme.Til

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl
) {
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
            { Icon(Icons.Default.Search, contentDescription = null, tint = Til) }
        } else null,
        trailingIcon = if (isRtl) {
            { Icon(Icons.Default.Search, contentDescription = null, tint = Til) }
        } else null,
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.height(56.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Til,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray
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