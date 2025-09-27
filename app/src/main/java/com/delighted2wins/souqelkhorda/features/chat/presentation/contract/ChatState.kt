package com.delighted2wins.souqelkhorda.features.chat.presentation.contract

import com.delighted2wins.souqelkhorda.features.chat.domain.entity.Message
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

data class ChatState(
    val messages: List<Message> = emptyList(),
    val messageText: String = "",
    val otherUser: MarketUser? = null,
    val currentUser: MarketUser? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
