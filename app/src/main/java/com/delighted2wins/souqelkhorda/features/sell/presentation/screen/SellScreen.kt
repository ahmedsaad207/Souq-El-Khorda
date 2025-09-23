package com.delighted2wins.souqelkhorda.features.sell.presentation.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
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
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.delighted2wins.souqelkhorda.core.components.CustomButton
import com.delighted2wins.souqelkhorda.core.components.CustomCard
import com.delighted2wins.souqelkhorda.core.enums.Destination
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.AddScrapSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.ItemsSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.OrderDestinationSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.OrderDetailsSection
import com.delighted2wins.souqelkhorda.features.sell.presentation.contract.SellIntent
import com.delighted2wins.souqelkhorda.features.sell.presentation.viewmodel.SellViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.net.toUri

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellScreen(
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: SellViewModel = hiltViewModel(),
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            it != SheetValue.Hidden
        }
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    val selectedDestination = remember { mutableStateOf(Destination.Company) }
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val price = remember { mutableIntStateOf(0) }
    val showError = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    val scrapToEdit = remember { mutableStateOf<Scrap?>(null) }

    LaunchedEffect(uiState.isScrapSaved) {
        if (uiState.isScrapSaved) {
            Toast.makeText(ctx, "Scrap added successfully!", Toast.LENGTH_SHORT).show()
            viewModel.resetScrapSavedFlag()
        }
    }

    LaunchedEffect(uiState.isScrapDeleted) {
        if (uiState.isScrapDeleted) {
            Toast.makeText(ctx, "Scrap deleted successfully!", Toast.LENGTH_SHORT).show()
            viewModel.resetScrapDeletedFlag()
        }
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
                    onEditClick = { scrap ->
                        scrapToEdit.value = scrap
                        isBottomSheetVisible = true
                    }
                ) {
                    isBottomSheetVisible = true
                }
            }

            item {
                if (uiState.data.isNotEmpty()) {
                    CustomCard(
                        title = "Order Details",
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
                        contentDescription = "Submit Order",
                        text = "Submit Order",
                        isLoading = isLoading,
                        textLoading = "Submitting Order",
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (title.value.isBlank()) {
                            showError.value = true
                            return@CustomButton
                        }

                        isLoading.value = true

                        val order = Order(
                            userId = FirebaseAuth.getInstance().uid.toString(),
                            scraps = uiState.data,
                            type = if (selectedDestination.value == Destination.Company) OrderType.SALE else OrderType.MARKET,
                            title = title.value.trim(),
                            description = description.value.trim(),
                            price = price.intValue
                        )

                        scope.launch {
                            delay(1000)
                            isLoading.value = false

                            viewModel.processIntent(SellIntent.SendOrder(order))
                            title.value = ""
                            description.value = ""
                        }

                    }

                    Spacer(Modifier.height(innerPadding.calculateBottomPadding()))
                }
            }
        }

        if (isBottomSheetVisible) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = { }
            ) {
                Box(
                    modifier = Modifier
                        .height(screenHeight * 0.8f)
                        .fillMaxWidth()
                ) {
                    AddScrapSection(
                        scrap = scrapToEdit,
                        onCancelClick = {
                            scrapToEdit.value = null
                            scope.launch {
                                bottomSheetState.hide()
                            }.invokeOnCompletion {
                                isBottomSheetVisible = false
                            }
                        },
                        onAddClick = { scrap ->
                            viewModel.processIntent(SellIntent.AddScrap(scrap))
                            scope.launch { bottomSheetState.hide() }
                                .invokeOnCompletion { isBottomSheetVisible = false }
                        }
                    )
                }

            }
        }
    }
}

fun uploadImage(uri: Uri) {
    MediaManager.get().upload(uri)
//        .unsigned("YOUR_UPLOAD_PRESET") // For unsigned upload
        .callback(object : UploadCallback {
            override fun onStart(requestId: String) {
                // upload started
            }

            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                // progress updates
            }

            override fun onSuccess(requestId: String, resultData: MutableMap<Any?, Any?>) {
                val imageUrl = resultData["secure_url"] as String
                // use the URL
                Log.d("TAG", "onSuccess: url: $imageUrl")
            }

            override fun onError(
                requestId: String?,
                error: ErrorInfo?
            ) {

            }

            override fun onReschedule(
                requestId: String?,
                error: ErrorInfo?
            ) {

            }
        }).dispatch()
}

