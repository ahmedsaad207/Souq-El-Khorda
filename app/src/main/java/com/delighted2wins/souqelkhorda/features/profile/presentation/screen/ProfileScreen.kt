package com.delighted2wins.souqelkhorda.features.profile.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.core.components.TowIconAppBar
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.CustomTextFieldWithIcon
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.HistoryButton
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.LogoutButton
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.ProfileHeader
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.ProfileHeaderBackground
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.ProfileStats
import com.delighted2wins.souqelkhorda.features.profile.presentation.contract.ProfileContract
import com.delighted2wins.souqelkhorda.features.profile.presentation.viewmodel.ProfileViewModel

@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val colors = MaterialTheme.colorScheme

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                ProfileHeaderBackground()
                ProfileHeader(
                    email = state.email.value,
                    name = state.name.value,
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
                EditableField(
                    label = "UserName",
                    state = state.name,
                    onValueChange = { viewModel.changeValue(it, { s -> s.name }) { st, f -> st.copy(name = f) } },
                    onStartEdit = { viewModel.startEditing({ it.name }) { st, f -> st.copy(name = f) } },
                    onCancel = { viewModel.cancelEditing({ it.name }) { st, f -> st.copy(name = f) } },
                    onSave = { viewModel.updateName() }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableField(
                    label = "Email",
                    state = state.email,
                    onValueChange = { viewModel.changeValue(it, { s -> s.email }) { st, f -> st.copy(email = f) } },
                    onStartEdit = { viewModel.startEditing({ it.email }) { st, f -> st.copy(email = f) } },
                    onCancel = { viewModel.cancelEditing({ it.email }) { st, f -> st.copy(email = f) } },
                    onSave = { viewModel.updateEmail() }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableField(
                    label = "Phone",
                    state = state.phone,
                    onValueChange = { viewModel.changeValue(it, { s -> s.phone }) { st, f -> st.copy(phone = f) } },
                    onStartEdit = { viewModel.startEditing({ it.phone }) { st, f -> st.copy(phone = f) } },
                    onCancel = { viewModel.cancelEditing({ it.phone }) { st, f -> st.copy(phone = f) } },
                    onSave = { viewModel.updatePhone() }
                )
                Spacer(modifier = Modifier.height(12.dp))
                EditableField(
                    label = "Governorate",
                    state = state.governorate,
                    onValueChange = { viewModel.changeValue(it, { s -> s.governorate }) { st, f -> st.copy(governorate = f) } },
                    onStartEdit = { viewModel.startEditing({ it.governorate }) { st, f -> st.copy(governorate = f) } },
                    onCancel = { viewModel.cancelEditing({ it.governorate }) { st, f -> st.copy(governorate = f) } },
                    onSave = { viewModel.updateGovernorate() }
                )
                Spacer(modifier = Modifier.height(12.dp))
                EditableField(
                    label = "Address",
                    state = state.address,
                    onValueChange = { viewModel.changeValue(it, { s -> s.address }) { st, f -> st.copy(address = f) } },
                    onStartEdit = { viewModel.startEditing({ it.address }) { st, f -> st.copy(address = f) } },
                    onCancel = { viewModel.cancelEditing({ it.address }) { st, f -> st.copy(address = f) } },
                    onSave = { viewModel.updateAddress() }
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

@Composable
fun EditableField(
    label: String,
    state: ProfileContract.ProfileFieldState,
    onValueChange: (String) -> Unit,
    onStartEdit: () -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    Column {
        if (state.isEditing) {
            OutlinedTextField(
                value = state.value,
                onValueChange = onValueChange,
                label = { Text(label) },
                trailingIcon = {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Row {
                            IconButton(onClick = onSave) {
                                Icon(Icons.Default.Check, contentDescription = "Save")
                            }
                            IconButton(onClick = onCancel) {
                                Icon(Icons.Default.Close, contentDescription = "Cancel")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            CustomTextFieldWithIcon(
                value = state.value,
                onValueChange = {},
                label = label,
                placeholder = state.value,
                onIconClick = onStartEdit,
                keyboardType = KeyboardType.Text,
                singleLine = true,
                readOnly = true,
                icon = when {
                    state.success -> Icons.Default.Check
                    state.error != null -> Icons.Default.Error
                    else -> Icons.Default.Edit
                }
            )
        }

        if (state.error != null) {
            Text(text = state.error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
    }
}




