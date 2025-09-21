package com.delighted2wins.souqelkhorda.features.profile.presentation.screen

import CustomTextFieldWithIcon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.TowIconAppBar
import com.delighted2wins.souqelkhorda.core.extensions.restartActivity
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

    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProfileContract.Effect.Logout -> onLogoutClick()
                ProfileContract.Effect.NavigateToHistory -> onHistoryClick()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box {
                ProfileHeaderBackground()
                ProfileHeader(
                    email = state.email.value,
                    name = state.name.value,
                    onEditAvatar = {  }
                )
                TowIconAppBar(
                    onStartClick = { onBackClick() },
                    onEndClick = { },
                    headerTxt = stringResource(R.string.profile),
                    onStartIcon = Icons.AutoMirrored.Default.ArrowBack,
                    onEnIcon = Icons.Default.Language,
                    isProfile = true,
                    onLanguageClick = { lang ->
                        viewModel.handleIntent(ProfileContract.Intent.ChangeLanguage(lang))
                        context.restartActivity()
                    }
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

                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = colors.surfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))

                EditableField(
                    label = stringResource(R.string.profile_username),
                    state = state.name,
                    onValueChange = { viewModel.handleIntent(ProfileContract.Intent.ChangeName(it)) },
                    onStartEdit = { viewModel.handleIntent(ProfileContract.Intent.StartEditing({ it.name }) { st, f -> st.copy(name = f) }) },
                    onCancel = { viewModel.handleIntent(ProfileContract.Intent.CancelEditing({ it.name }) { st, f -> st.copy(name = f) }) },
                    onSave = { viewModel.handleIntent(ProfileContract.Intent.SaveName) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableField(
                    label = stringResource(R.string.profile_email),
                    state = state.email,
                    onValueChange = { viewModel.handleIntent(ProfileContract.Intent.ChangeEmail(it)) },
                    onStartEdit = { viewModel.handleIntent(ProfileContract.Intent.StartEditing({ it.email }) { st, f -> st.copy(email = f) }) },
                    onCancel = { viewModel.handleIntent(ProfileContract.Intent.CancelEditing({ it.email }) { st, f -> st.copy(email = f) }) },
                    onSave = { viewModel.handleIntent(ProfileContract.Intent.SaveEmail) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableField(
                    label = stringResource(R.string.profile_phone),
                    state = state.phone,
                    onValueChange = { viewModel.handleIntent(ProfileContract.Intent.ChangePhone(it)) },
                    onStartEdit = { viewModel.handleIntent(ProfileContract.Intent.StartEditing({ it.phone }) { st, f -> st.copy(phone = f) }) },
                    onCancel = { viewModel.handleIntent(ProfileContract.Intent.CancelEditing({ it.phone }) { st, f -> st.copy(phone = f) }) },
                    onSave = { viewModel.handleIntent(ProfileContract.Intent.SavePhone) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableField(
                    label = stringResource(R.string.profile_governorate),
                    state = state.governorate,
                    onValueChange = { viewModel.handleIntent(ProfileContract.Intent.ChangeGovernorate(it)) },
                    onStartEdit = { viewModel.handleIntent(ProfileContract.Intent.StartEditing({ it.governorate }) { st, f -> st.copy(governorate = f) }) },
                    onCancel = { viewModel.handleIntent(ProfileContract.Intent.CancelEditing({ it.governorate }) { st, f -> st.copy(governorate = f) }) },
                    onSave = { viewModel.handleIntent(ProfileContract.Intent.SaveGovernorate) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableField(
                    label = stringResource(R.string.profile_address),
                    state = state.address,
                    onValueChange = { viewModel.handleIntent(ProfileContract.Intent.ChangeAddress(it)) },
                    onStartEdit = { viewModel.handleIntent(ProfileContract.Intent.StartEditing({ it.address }) { st, f -> st.copy(address = f) }) },
                    onCancel = { viewModel.handleIntent(ProfileContract.Intent.CancelEditing({ it.address }) { st, f -> st.copy(address = f) }) },
                    onSave = { viewModel.handleIntent(ProfileContract.Intent.SaveAddress) }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = colors.surfaceVariant)

                Spacer(modifier = Modifier.height(16.dp))
                HistoryButton(onClick = { viewModel.handleIntent(ProfileContract.Intent.NavigateToHistory) })
                Spacer(modifier = Modifier.height(16.dp))
                LogoutButton(onLogout = { viewModel.handleIntent(ProfileContract.Intent.Logout) })
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
        CustomTextFieldWithIcon(
            value = state.value,
            onValueChange = if (state.isEditing) onValueChange else { _ -> },
            label = label,
            placeholder = state.value.ifEmpty { label },
            onIconClick = { if (!state.isEditing) onStartEdit() },
            imageVectorIcon = when {
                state.isEditing -> null
                state.success -> Icons.Default.Check
                state.error != null -> Icons.Default.Error
                else -> Icons.Default.Edit
            },
            trailingContent = if (state.isEditing) {
                {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = onSave) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                            }
                            IconButton(onClick = onCancel) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel")
                            }
                        }
                    }
                }
            } else null,
            readOnly = !state.isEditing,
            keyboardType = KeyboardType.Text,
            singleLine = true
        )

        if (state.error != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}





