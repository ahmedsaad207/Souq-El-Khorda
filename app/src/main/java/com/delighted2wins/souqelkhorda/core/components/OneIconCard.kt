package com.delighted2wins.souqelkhorda.core.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.app.theme.AppTypography


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun OneIconCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    headerTxt: String = stringResource(R.string.home_screen),
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    titleSize: Int = 20
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy((screenWidth * 0.175).dp)
    ) {
        TapBarBtn(onIconClick = { onClick() }, icon = icon)
        Text(
            text = headerTxt,
            style = AppTypography.titleLarge.copy(fontSize = titleSize.sp, fontWeight = FontWeight.Bold),
        )

    }

}
