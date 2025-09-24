package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.enums.LanguageEnum

@Composable
fun TowIconAppBar(
    modifier: Modifier = Modifier,
    onStartClick: () -> Unit = {},
    onEndClick: () -> Unit = {},
    headerTxt: String = "Home",
    onStartIcon: ImageVector = Icons.Default.Call,
    onEnIcon: ImageVector = Icons.Default.Person,
    isProfile: Boolean = false,
    onLanguageClick: (String) -> Unit = {}
) {
    var showSheet by remember { mutableStateOf(false) }
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularIconButton(
            icon = onStartIcon,
            onClick = { onStartClick() },
            tint = colors.onPrimary
        )

        Text(
            text = headerTxt,
            style = typography.titleLarge.copy(color = colors.onPrimary)
        )

        Box {
            CircularIconButton(
                icon = onEnIcon,
                onClick = {
                    onEndClick()
                    showSheet = !showSheet
                },
                tint = colors.onPrimary
            )
            if (isProfile) {
                LanguageBottomSheet(
                    showSheet = showSheet,
                    onDismiss = { showSheet = false },
                    onLanguageClick = { lang ->
                        onLanguageClick(lang.code)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    onLanguageClick: (LanguageEnum) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = colors.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Choose Language",
                    style = typography.titleMedium.copy(color = colors.onSurface),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LanguageEnum.entries.forEach { lang ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onLanguageClick(lang)
                                onDismiss()
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val icon = when (lang.code) {
                            LanguageEnum.ENGLISH.code -> painterResource(R.drawable.uk)
                            LanguageEnum.ARABIC.code -> painterResource(R.drawable.egypt)
                            LanguageEnum.DEFAULT.code -> painterResource(R.drawable.system)
                            else -> null
                        }

                        icon?.let {
                            Icon(
                                painter = it,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp),
                                tint = Color.Unspecified
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = LanguageEnum.getValue(lang.code),
                            style = typography.bodyLarge.copy(color = colors.onSurface)
                        )
                    }
                }
            }
        }
    }
}

