package com.delighted2wins.souqelkhorda.features.offers.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.app.theme.Til
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeOfferBottomSheet(
    orderId: String,
    offerMaker: MarketUser?,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    onSubmitOffer: (Offer) -> Unit,
    isRtl: Boolean
) {
    offerMaker?.let { user ->
        var offerAmount by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            DirectionalText(
                text = if (isRtl) "تقديم عرض" else "Make an Offer",
                style = MaterialTheme.typography.titleLarge,
                color = Til,
                contentIsRtl = isRtl
            )

            Spacer(modifier = Modifier.height(16.dp))

            val previewOffer = offerAmount.toIntOrNull()?.takeIf { it > 0 }?.let {
                Offer(
                    offerId = "",
                    orderId = orderId,
                    buyerId = user.name,
                    offerPrice = it,
                    status = OfferStatus.PENDING,
                    date = System.currentTimeMillis()
                )
            }

            previewOffer?.let {
                OfferCard(
                    offer = it,
                    buyerName = user.name,
                    isRtl = isRtl
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            OutlinedTextField(
                value = offerAmount,
                onValueChange = {
                    offerAmount = it
                    errorMessage = ""
                },
                label = { Text(if (isRtl) "المبلغ" else "Amount") },
                isError = errorMessage.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
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

            Button(
                onClick = {
                    when {
                        offerAmount.isBlank() -> errorMessage =
                            if (isRtl) "الرجاء إدخال المبلغ" else "Please enter an amount"
                        offerAmount.toIntOrNull() == null -> errorMessage =
                            if (isRtl) "المبلغ غير صالح" else "Invalid amount"
                        offerAmount.toInt() <= 0 -> errorMessage =
                            if (isRtl) "المبلغ يجب أن يكون أكبر من صفر" else "Amount must be greater than zero"
                        else -> {
                            val newOffer = Offer(
                                offerId = "",
                                orderId = orderId,
                                buyerId = user.id,
                                offerPrice = offerAmount.toInt(),
                                status = OfferStatus.PENDING,
                                date = System.currentTimeMillis()
                            )
                            onSubmitOffer(newOffer)
                            coroutineScope.launch { sheetState.hide() }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                DirectionalText(
                    text = if (isRtl) "إرسال العرض" else "Submit Offer",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    contentIsRtl = isRtl,
                )
            }
        }
    }
}
