package com.delighted2wins.souqelkhorda.features.profile.presentation.screen

import CustomTextFieldWithIcon
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.TowIconAppBar
import com.delighted2wins.souqelkhorda.core.enums.GovernorateEnum
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.extensions.convertNumbersToArabic
import com.delighted2wins.souqelkhorda.core.extensions.restartActivity
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.HistoryButton
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.LogoutButton
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.ProfileHeader
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.ProfileHeaderBackground
import com.delighted2wins.souqelkhorda.features.profile.presentation.component.ProfileStats
import com.delighted2wins.souqelkhorda.features.profile.presentation.contract.ProfileContract
import com.delighted2wins.souqelkhorda.features.profile.presentation.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    snackBarState: SnackbarHostState,
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
                is ProfileContract.Effect.ShowSnackbar -> snackBarState.showSnackbar(effect.message)
                ProfileContract.Effect.Logout -> onLogoutClick()
                ProfileContract.Effect.NavigateToHistory -> onHistoryClick()
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.handleIntent(ProfileContract.Intent.ChangeImageUrl(uri.toString()))
                viewModel.handleIntent(ProfileContract.Intent.SaveImageUrl)
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box {
                ProfileHeaderBackground()
                ProfileHeader(
                    modifier = Modifier.statusBarsPadding(),
                    email = state.email.value,
                    name = state.name.value,
                    imageUrl = state.imageUrl.value,
                    isBuyer = state.isBuyer,
                    onEditAvatar = {
                        launcher.launch(arrayOf("image/*"))
                    }
                )
                TowIconAppBar(
                    modifier = Modifier.statusBarsPadding(),
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
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
            ) {
                if (state.isLoadingOrders) {
                    ProfileStats(
                        stats = listOf(
                            null to OrderStatus.COMPLETED.getLocalizedValue(),
                            null to OrderStatus.PENDING.getLocalizedValue(),
                            null to OrderStatus.CANCELLED.getLocalizedValue()
                        )
                    )
                } else {
                    ProfileStats(
                        stats = listOf(
                            state.cancelledCount.toString().convertNumbersToArabic() to OrderStatus.CANCELLED.getLocalizedValue(),
                            state.pendingCount.toString().convertNumbersToArabic() to OrderStatus.PENDING.getLocalizedValue(),
                            state.completedCount.toString().convertNumbersToArabic() to OrderStatus.COMPLETED.getLocalizedValue()
                        )
                    )
                }

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = colors.surfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))

                EditableField(
                    label = stringResource(R.string.profile_username),
                    state = state.name,
                    onValueChange = { viewModel.handleIntent(ProfileContract.Intent.ChangeName(it)) },
                    onStartEdit = {
                        viewModel.handleIntent(ProfileContract.Intent.StartEditing({ it.name }) { st, f ->
                            st.copy(
                                name = f
                            )
                        })
                    },
                    onCancel = {
                        viewModel.handleIntent(ProfileContract.Intent.CancelEditing({ it.name }) { st, f ->
                            st.copy(
                                name = f
                            )
                        })
                    },
                    onSave = { viewModel.handleIntent(ProfileContract.Intent.SaveName) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableField(
                    label = stringResource(R.string.profile_phone),
                    state = state.phone,
                    onValueChange = { viewModel.handleIntent(ProfileContract.Intent.ChangePhone(it)) },
                    onStartEdit = {
                        viewModel.handleIntent(ProfileContract.Intent.StartEditing({ it.phone }) { st, f ->
                            st.copy(
                                phone = f
                            )
                        })
                    },
                    onCancel = {
                        viewModel.handleIntent(ProfileContract.Intent.CancelEditing({ it.phone }) { st, f ->
                            st.copy(
                                phone = f
                            )
                        })
                    },
                    onSave = { viewModel.handleIntent(ProfileContract.Intent.SavePhone) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableDropdownField(
                    label = stringResource(R.string.profile_governorate),
                    state = state.governorate,
                    options = GovernorateEnum.getAllGovernorate(),
                    onValueChange = {
                        viewModel.handleIntent(
                            ProfileContract.Intent.ChangeGovernorate(it)
                        )
                    },
                    onStartEdit = {
                        viewModel.handleIntent(
                            ProfileContract.Intent.StartEditing({ it.governorate }) { st, f ->
                                st.copy(governorate = f)
                            }
                        )
                    },
                    onCancel = {
                        viewModel.handleIntent(
                            ProfileContract.Intent.CancelEditing({ it.governorate }) { st, f ->
                                st.copy(governorate = f)
                            }
                        )
                    },
                    onSave = {
                        viewModel.handleIntent(ProfileContract.Intent.SaveGovernorate)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableField(
                    label = stringResource(R.string.area),
                    state = state.area,
                    onValueChange = { viewModel.handleIntent(ProfileContract.Intent.ChangeArea(it)) },
                    onStartEdit = {
                        viewModel.handleIntent(ProfileContract.Intent.StartEditing({ it.area }) { st, f ->
                            st.copy(
                                area = f
                            )
                        })
                    },
                    onCancel = {
                        viewModel.handleIntent(ProfileContract.Intent.CancelEditing({ it.area }) { st, f ->
                            st.copy(
                                area = f
                            )
                        })
                    },
                    onSave = { viewModel.handleIntent(ProfileContract.Intent.SaveArea) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditableField(
                    label = stringResource(R.string.profile_address),
                    state = state.address,
                    onValueChange = { viewModel.handleIntent(ProfileContract.Intent.ChangeAddress(it)) },
                    onStartEdit = {
                        viewModel.handleIntent(ProfileContract.Intent.StartEditing({ it.address }) { st, f ->
                            st.copy(
                                address = f
                            )
                        })
                    },
                    onCancel = {
                        viewModel.handleIntent(ProfileContract.Intent.CancelEditing({ it.address }) { st, f ->
                            st.copy(
                                address = f
                            )
                        })
                    },
                    onSave = { viewModel.handleIntent(ProfileContract.Intent.SaveAddress) }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = colors.surfaceVariant
                )

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
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = onSave) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Save",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = onCancel) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cancel",
                                    tint = MaterialTheme.colorScheme.error
                                )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableDropdownField(
    label: String,
    state: ProfileContract.ProfileFieldState,
    options: List<String>,
    onValueChange: (String) -> Unit,
    onStartEdit: () -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()


    Column {
        CustomTextFieldWithIcon(
            value = state.value,
            onValueChange = {},
            label = label,
            placeholder = label,
            readOnly = true,
            onIconClick = {
                if (!state.isEditing) {
                    onStartEdit()
                }
                showSheet = true
            },
            imageVectorIcon = when {
                state.success -> Icons.Default.Check
                state.error != null -> Icons.Default.Error
                else -> Icons.Default.Edit
            },
            trailingContent = if (state.isEditing) {
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        } else {
                            IconButton(onClick = onSave) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Save", tint = colors.primary)
                            }
                            IconButton(onClick = onCancel) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel", tint = colors.error)
                            }
                        }
                    }
                }
            } else null
        )

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(text = label, style = MaterialTheme.typography.titleMedium)
                }
                Divider()
                LazyColumn {
                    items(options) { option ->
                        val isSelected = option == state.value
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isSelected) colors.surfaceVariant else Color.Transparent)
                                .clickable {
                                    onValueChange(option)
                                    scope.launch {
                                        sheetState.hide()
                                        showSheet = false
                                    }
                                }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (isSelected) colors.primary else colors.onBackground
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = colors.primary)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
            LaunchedEffect(Unit) {
                scope.launch { sheetState.partialExpand() }
            }
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Error, contentDescription = null, tint = colors.error, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = state.error, color = colors.error, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}







