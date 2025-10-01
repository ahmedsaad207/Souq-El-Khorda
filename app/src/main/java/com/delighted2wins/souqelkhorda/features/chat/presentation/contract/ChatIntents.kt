package com.delighted2wins.souqelkhorda.features.chat.presentation.contract

sealed class ChatIntent {
    data class SetMessageText(val text: String) : ChatIntent()
    data class SendMessage(val message: String) : ChatIntent()
    data class LoadMessages(val orderId: String, val offerId: String) : ChatIntent()
}
