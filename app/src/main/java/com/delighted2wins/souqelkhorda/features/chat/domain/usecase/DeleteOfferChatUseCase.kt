package com.delighted2wins.souqelkhorda.features.chat.domain.usecase

import com.delighted2wins.souqelkhorda.features.chat.domain.repository.ChatRepository
import javax.inject.Inject

class DeleteOfferChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(orderId: String, offerId: String) : Boolean {
        return chatRepository.deleteChat(orderId, offerId)
    }
}