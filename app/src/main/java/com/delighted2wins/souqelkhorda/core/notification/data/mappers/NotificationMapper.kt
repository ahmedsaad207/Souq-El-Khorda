package com.delighted2wins.souqelkhorda.core.notification.data.mappers

import com.delighted2wins.souqelkhorda.core.notification.data.model.ApiResponseDto
import com.delighted2wins.souqelkhorda.core.notification.data.model.NotificationRequestDto
import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationRequest
import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationResponse


fun NotificationRequestDto.toDomain() : NotificationRequest {
    return NotificationRequest(
        toUserId = toToken,
        title = title,
        message = message,
        imageUrl = imageUrl,
        action = action
    )
}

fun NotificationRequest.toDto() : NotificationRequestDto {
    return NotificationRequestDto(
        toToken = token ?: "",
        toUserId = toUserId ?: "",
        title = title,
        message = message,
        imageUrl = imageUrl,
        action = action
    )
}

fun ApiResponseDto<Unit>.toDomain() : NotificationResponse<Unit> {
    return NotificationResponse(
        success = success,
        message = message,
        data = data
    )
}

fun NotificationResponse<Unit>.toDto() : ApiResponseDto<Unit> {
    return ApiResponseDto(
        success = success,
        message = message,
        data = data
    )
}
