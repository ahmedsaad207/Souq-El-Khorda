package com.delighted2wins.souqelkhorda.app

import android.app.Application
import com.cloudinary.android.MediaManager
import com.delighted2wins.souqelkhorda.core.notification.utils.FcmTokenManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var fcmTokenManager: FcmTokenManager

    override fun onCreate() {
        super.onCreate()
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }
        setupAuthStateListener()
        val cloudinaryConfig = mapOf(
            "cloud_name" to "souq-el-khorda",
            "api_key" to "821326922172527",
            "api_secret" to "azW21Qp0bpfortIgiNAbAExJNTw"
        )
        MediaManager.init(this, cloudinaryConfig)
    }

    private fun setupAuthStateListener() {
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            auth.currentUser?.let { user ->
                CoroutineScope(Dispatchers.IO).launch {
                    fcmTokenManager.syncTokenToFirestore(user.uid)
                }
            }
        }
    }
}