package com.delighted2wins.souqelkhorda.core.notification.data.remote.datasource

import com.delighted2wins.souqelkhorda.core.notification.data.model.ApiResponseDto
import com.delighted2wins.souqelkhorda.core.notification.data.model.NotificationRequestDto
import com.delighted2wins.souqelkhorda.core.notification.data.remote.service.FcmApiService
import javax.inject.Inject

class FcmRemoteDataSourceImpl @Inject constructor(
    private val fcmApiService: FcmApiService
) : FcmRemoteDataSource {
    override suspend fun sendNotification(request: NotificationRequestDto): ApiResponseDto<Unit> {
        return fcmApiService.sendNotification(request)
    }
}