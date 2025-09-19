package com.delighted2wins.souqelkhorda.features.profile.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.TowIconAppBar
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.CustomTextFieldWithIcon
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.HistoryButton
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.LogoutButton
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.ProfileHeader
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.ProfileHeaderBackground
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.ProfileStats

@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    var email by remember { mutableStateOf("abdelaziz.maher@gmail.com") }
    var phone by remember { mutableStateOf("01062894289") }
    var governrate by remember { mutableStateOf("alexandria") }
    var address by remember { mutableStateOf("elSyouf, Shamaa, Gamal-AbouHread Street") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                ProfileHeaderBackground()
                ProfileHeader(
                    email = email,
                    name = "Abdelaziz Maher",
                    onEditAvatar = { }
                )
                TowIconAppBar(
                    onStartClick = { onBackClick() },
                    onEndClick = { },
                    headerTxt = "Profile",
                    onStartIcon = Icons.Default.ArrowBack,
                    onEnIcon = Icons.Default.Language,
                    isProfile = true,
                    onLanguageClick = { }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProfileStats(
                    stats = listOf(
                        "47" to "Completed",
                        "1.2k" to "Pending",
                        "23" to "Canceled"
                    )
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = colors.surfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextFieldWithIcon(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email Address",
                    placeholder = email,
                    onIconClick = { },
                    keyboardType = KeyboardType.Email,
                    singleLine = true,
                    readOnly = true,
                    icon = Icons.Default.Edit
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextFieldWithIcon(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "Phone Number",
                    placeholder = phone,
                    onIconClick = { },
                    keyboardType = KeyboardType.Phone,
                    singleLine = true,
                    readOnly = true,
                    icon = Icons.Default.Edit
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextFieldWithIcon(
                    value = governrate,
                    onValueChange = { governrate = it },
                    label = "Governrate",
                    placeholder = governrate,
                    onIconClick = { },
                    keyboardType = KeyboardType.Text,
                    singleLine = false,
                    readOnly = true,
                    icon = Icons.Default.Edit
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextFieldWithIcon(
                    value = address,
                    onValueChange = { address = it },
                    label = "Address",
                    placeholder = address,
                    onIconClick = { },
                    keyboardType = KeyboardType.Text,
                    singleLine = false,
                    readOnly = true,
                    icon = Icons.Default.Edit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = colors.surfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                HistoryButton(onClick = onHistoryClick)
                Spacer(modifier = Modifier.height(16.dp))
                LogoutButton(onLogout = onLogoutClick)
            }
        }
    }
}



