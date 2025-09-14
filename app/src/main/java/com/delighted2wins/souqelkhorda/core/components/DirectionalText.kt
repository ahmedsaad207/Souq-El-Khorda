package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection


@Composable
fun DirectionalText(
    text: String,
    contentIsRtl: Boolean,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign? = TextAlign.Start,
    softWrap: Boolean = true,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides if (contentIsRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Box(modifier = modifier.fillMaxWidth())
        {
            Text(
                text = text,
                modifier = modifier,
                color = color,
                maxLines = maxLines,
                overflow = overflow,
                textAlign = textAlign,
                softWrap = softWrap,
                style = style
            )
        }
    }
}
