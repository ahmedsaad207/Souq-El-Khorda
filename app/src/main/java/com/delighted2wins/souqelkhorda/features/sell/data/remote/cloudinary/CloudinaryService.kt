package com.delighted2wins.souqelkhorda.features.sell.data.remote.cloudinary

import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CloudinaryService {
    suspend fun uploadImage(uri: Uri): String = suspendCoroutine { continuation ->
        MediaManager.get().upload(uri)
            .callback(object : UploadCallback {
                override fun onStart(requestId: String?) {
                }

                override fun onProgress(
                    requestId: String?,
                    bytes: Long,
                    totalBytes: Long
                ) {
                }

                override fun onSuccess(
                    requestId: String?,
                    resultData: Map<*, *>?
                ) {
                    val imageUrl = resultData?.get("secure_url") as? String
                    if (imageUrl != null) {
                        continuation.resume(imageUrl)
                    } else {
                        continuation.resume("")
                    }
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
}