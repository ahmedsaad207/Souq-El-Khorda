package com.delighted2wins.souqelkhorda.features.chat.domain.usecase

import com.delighted2wins.souqelkhorda.features.chat.domain.repository.ChatRepository
import javax.inject.Inject

class DeleteChatsByOrderIdUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(orderId: String, offerIds: List<String>) : Boolean {
        return chatRepository.deleteChatsByOrderId(orderId, offerIds)
    }
}