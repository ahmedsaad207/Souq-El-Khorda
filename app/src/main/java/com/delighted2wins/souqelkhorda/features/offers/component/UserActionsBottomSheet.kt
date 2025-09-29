package com.delighted2wins.souqelkhorda.features.offers

import WarningCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
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
    onConfirmAction: suspend (Any) -> Unit,
    isSubmitting: Boolean,
    isRtl: Boolean,
    actionType: BottomSheetActionType
) {
    var offerAmount by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isRtl) actionType.arValue else actionType.enValue,
                style = MaterialTheme.typography.titleLarge,
                color = actionType.color
            )
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
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if ((actionType == BottomSheetActionType.MAKE_OFFER || actionType == BottomSheetActionType.UPDATE_OFFER) && offerMaker != null) {
            OutlinedTextField(
                value = offerAmount,
                onValueChange = { input ->
                    offerAmount = input.filter { it.isDigit() }
                    errorMessage = ""
                },
                label = { Text(stringResource(R.string.label_amount)) },
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
                coroutineScope.launch {
                    when (actionType) {
                        BottomSheetActionType.MAKE_OFFER, BottomSheetActionType.UPDATE_OFFER -> {
                            val amount = offerAmount.toIntOrNull()
                            when {
                                offerAmount.isBlank() -> errorMessage = context.getString(R.string.enter_amount)
                                amount == null -> errorMessage = context.getString(R.string.invalid_amount)
                                amount <= 0 -> errorMessage = context.getString(R.string.amount_greater_than_zero)
                                else -> offerMaker?.let { user ->
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
                        }
                        BottomSheetActionType.ACCEPT_OFFER -> {
                            coroutineScope.launch {
                                onConfirmAction(Unit)
                            }
                        }
                        BottomSheetActionType.REJECT_OFFER -> {
                            coroutineScope.launch {
                                onConfirmAction(Unit)
                            }
                        }
                        BottomSheetActionType.DELETE_OFFER -> {
                            coroutineScope.launch {
                                onConfirmAction(Unit)
                            }
                        }
                        BottomSheetActionType.COMPLETE_ORDER -> {
                            coroutineScope.launch {
                                onConfirmAction(Unit)
                            }
                        }
                        BottomSheetActionType.CANCEL_COMPANY_ORDER -> {
                            coroutineScope.launch {
                                onConfirmAction(Unit)
                            }
                        }
                        BottomSheetActionType.MARK_RECEIVED -> {
                            coroutineScope.launch {
                                onConfirmAction(Unit)
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSubmitting,
            colors = ButtonDefaults.buttonColors(
                containerColor = actionType.color
            )
        ) {
            if (isSubmitting) {
                DotLoadingIndicator()
            } else {
                Text(
                    text = if (isRtl) actionType.arValue else actionType.enValue,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }
    }
}

