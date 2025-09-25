package com.delighted2wins.souqelkhorda.features.offers

import WarningCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.enums.BottomSheetActionType
import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.features.authentication.presentation.component.DotLoadingIndicator
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserActionsBottomSheet(
    orderId: String,
    offerMaker: MarketUser?,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    onConfirmAction: suspend (Offer) -> Unit,
    isSubmittingOffer: Boolean,
    isRtl: Boolean,
    actionType: BottomSheetActionType
) {
    offerMaker?.let { user ->
        var offerAmount by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isRtl) actionType.arValue else actionType.enValue,
                    style = MaterialTheme.typography.titleLarge,
                    color = actionType.color,
                )
//                IconButton(
//                    onClick = { coroutineScope.launch { sheetState.hide() } },
//                    enabled = !isSubmittingOffer
//                ) {
//                    Icon(
//                        Icons.Default.Close,
//                        contentDescription = "Close",
//                        tint = Color.Black
//                    )
//                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            actionType.warnings.forEach { (enMsg, arMsg) ->
                WarningCard(
                    message = if (isRtl) arMsg else enMsg,
                    warningColor = actionType.warningColor,
                    icon = {
                        Icon(
                            imageVector = actionType.warningIcon,
                            contentDescription = "Warning",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (actionType == BottomSheetActionType.MAKE_OFFER ||
                actionType == BottomSheetActionType.UPDATE_STATUS_OFFER
            ) {
                OutlinedTextField(
                    value = offerAmount,
                    onValueChange = { input ->
                        offerAmount = input.filter { it.isDigit() }
                        errorMessage = ""
                    },
                    label = { Text(if (isRtl) "المبلغ" else "Amount") },
                    isError = errorMessage.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    if (actionType == BottomSheetActionType.MAKE_OFFER ||
                        actionType == BottomSheetActionType.UPDATE_STATUS_OFFER
                    ) {
                        val amount = offerAmount.toIntOrNull()
                        when {
                            offerAmount.isBlank() -> errorMessage =
                                if (isRtl) "الرجاء إدخال المبلغ" else "Please enter an amount"
                            amount == null -> errorMessage =
                                if (isRtl) "المبلغ غير صالح" else "Invalid amount"
                            amount <= 0 -> errorMessage =
                                if (isRtl) "المبلغ يجب أن يكون أكبر من صفر" else "Amount must be greater than zero"
                            else -> coroutineScope.launch {
                                onConfirmAction(
                                    Offer(
                                        offerId = "",
                                        orderId = orderId,
                                        buyerId = user.id,
                                        offerPrice = amount,
                                        status = OfferStatus.PENDING,
                                        date = System.currentTimeMillis()
                                    )
                                )
                            }
                        }
                    } else {
                        coroutineScope.launch {
                            onConfirmAction(
                                Offer(
                                    offerId = "",
                                    orderId = orderId,
                                    buyerId = user.id,
                                    offerPrice = 0,
                                    status = OfferStatus.PENDING,
                                    date = System.currentTimeMillis()
                                )
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmittingOffer,
                colors = ButtonDefaults.buttonColors(containerColor = actionType.color)
            ) {
                if (isSubmittingOffer) {
                    DotLoadingIndicator()
                } else {
                    Text(
                        text = if (isRtl) actionType.arValue else actionType.enValue,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
