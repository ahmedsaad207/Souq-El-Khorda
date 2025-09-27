package com.delighted2wins.souqelkhorda.features.sell.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.ConfirmationDialog
import com.delighted2wins.souqelkhorda.core.components.CustomButton
import com.delighted2wins.souqelkhorda.core.components.CustomCard
import com.delighted2wins.souqelkhorda.core.enums.BottomSheetMode
import com.delighted2wins.souqelkhorda.core.enums.Destination
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.BottomSheetSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.ItemsSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.OrderDestinationSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.OrderDetailsSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.contract.SellIntent
import com.delighted2wins.souqelkhorda.features.sell.presentation.viewmodel.SellViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellScreen(
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: SellViewModel = hiltViewModel(),
    snackBarState: SnackbarHostState,
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            it != SheetValue.Hidden
        }
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val isBottomSheetVisible = remember { mutableStateOf(false) }

    val selectedDestination = remember { mutableStateOf(Destination.Company) }
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val price = remember { mutableIntStateOf(0) }
    val showError = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    val bottomSheetMode = remember { mutableStateOf(BottomSheetMode.ADD) }
    var currentScrap by remember { mutableStateOf<Scrap?>(null) }
    var scrapToDelete by remember { mutableStateOf<Scrap?>(null) }

    LaunchedEffect(uiState.isScrapSaved) {
        if (uiState.isScrapSaved) {
            snackBarState.showSnackbar(
                message = context.getString(R.string.scrap_added_successfully),
                duration = SnackbarDuration.Short
            )
            viewModel.resetScrapSavedFlag()
        }
    }

    LaunchedEffect(uiState.isScrapDeleted) {
        if (uiState.isScrapDeleted) {

            snackBarState.currentSnackbarData?.dismiss()

            snackBarState.showSnackbar(
                message = context.getString(R.string.scrap_deleted_successfully),
                duration = SnackbarDuration.Short
            )
            viewModel.resetScrapDeletedFlag()
        }
    }
    LaunchedEffect(uiState.isOrderSubmitted) {
        if (uiState.isOrderSubmitted) {
            snackBarState.showSnackbar(
                message = context.getString(R.string.order_submitted_successfully),
                duration = SnackbarDuration.Short
            )
            isLoading.value = false
            title.value = ""
            description.value = ""
            price.intValue = 0
            viewModel.resetOrderSubmittedFlag()
        }
    }

    if (scrapToDelete != null) {
        ConfirmationDialog(
            title =stringResource(R.string.confirm_delete),
            message = stringResource(R.string.are_you_sure_you_want_to_delete_this_scrap),
            confirmLabel = stringResource(R.string.delete),
            onConfirm = {
                viewModel.processIntent(SellIntent.DeleteScrap(scrapToDelete!!))
                scrapToDelete = null
            },
            onDismiss = { scrapToDelete = null }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                OrderDestinationSection(
                    selectedDestination = selectedDestination,
                )
            }

            item {
                ItemsSection(
                    uiState.data,
                    onEdit = { scrap ->
                        currentScrap = scrap
                        bottomSheetMode.value = BottomSheetMode.EDIT
                        isBottomSheetVisible.value = true
                    },
                    onAddItem = {
                        bottomSheetMode.value = BottomSheetMode.ADD
                        isBottomSheetVisible.value = true
                    },
                    onDelete = {
                        scrapToDelete = it
                    }
                )
            }

            item {
                if (uiState.data.isNotEmpty()) {
                    CustomCard(
                        title = stringResource(R.string.order_details),
                        color = Color(0xFF9144d4)
                    ) {
                        OrderDetailsSection(
                            title = title,
                            description = description,
                            price = price,
                            showError = showError,
                            selectedDestination = selectedDestination
                        )
                    }

                    CustomButton(
                        icon = Icons.Default.Sync,
                        contentDescription = stringResource(R.string.submit_order),
                        text = stringResource(R.string.submit_order),
                        isLoading = isLoading,
                        textLoading = "",
                        hasElevation = false,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (title.value.isBlank()) {
                            showError.value = true
                            return@CustomButton
                        }

                        val order = Order(
                            userId = FirebaseAuth.getInstance().uid.toString(),
                            scraps = uiState.data,
                            type = if (selectedDestination.value == Destination.Company) OrderType.SALE else OrderType.MARKET,
                            title = title.value.trim(),
                            description = description.value.trim(),
                            price = price.intValue
                        )
                        isLoading.value = true
                        viewModel.processIntent(SellIntent.SendOrder(order))
                    }

                    Spacer(Modifier.height(innerPadding.calculateBottomPadding()))
                }
            }
        }

        if (isBottomSheetVisible.value) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = {
                    hideBottomSheet(
                        scope,
                        bottomSheetState,
                        isBottomSheetVisible
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .height(screenHeight * 0.8f)
                        .fillMaxWidth()
                ) {
                    BottomSheetSection(
                        mode = bottomSheetMode.value,
                        scrap = currentScrap,
                        onCancelClick = {
                            hideBottomSheet(scope, bottomSheetState, isBottomSheetVisible)
                        },
                        onAddScrapClick = { scrap ->
                            viewModel.processIntent(SellIntent.AddScrap(scrap))
                            hideBottomSheet(scope, bottomSheetState, isBottomSheetVisible)
                        },
                        onUpdateScrapClick = {
                            viewModel.processIntent(SellIntent.UpdateScrap(it))
                            hideBottomSheet(scope, bottomSheetState, isBottomSheetVisible)
                        }
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun hideBottomSheet(
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    isBottomSheetVisible: MutableState<Boolean>
) {
    scope.launch {
        bottomSheetState.hide()
    }.invokeOnCompletion {
        isBottomSheetVisible.value = false
    }
}

