package com.delighted2wins.souqelkhorda.features.market.presentation.component.orderdetails

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.delighted2wins.souqelkhorda.core.components.DirectionalText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsTopBar(
    title: String,
    isRtl: Boolean = false,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            DirectionalText(
                text = title,
                contentIsRtl = isRtl,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}
