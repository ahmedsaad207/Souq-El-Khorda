package com.delighted2wins.souqelkhorda.core.notification.utils

import android.content.Context
import androidx.core.content.edit
import com.delighted2wins.souqelkhorda.core.AppConstant
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FcmTokenManager @Inject constructor(
    context: Context
) {
    private val prefs = context.getSharedPreferences(
        AppConstant.SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

    fun cacheToken(token: String) {
        prefs.edit { putString("fcm_token", token) }
    }

    fun clearCachedToken() {
        prefs.edit { remove("fcm_token") }
    }

    suspend fun syncTokenToFirestore(uid: String) {

        try {
            val token = FirebaseMessaging.getInstance().token.await()
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .set(mapOf("fcmToken" to token), SetOptions.merge())
                .await()
            clearCachedToken()
        } catch (e: Exception) {
            
        }
    }



}

