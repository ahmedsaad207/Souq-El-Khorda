package com.delighted2wins.souqelkhorda.app

import android.app.Application
import com.cloudinary.Cloudinary
import com.cloudinary.android.MediaManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }

        val cloudinaryConfig = mapOf(
            "cloud_name" to "souq-el-khorda",
            "api_key" to "821326922172527",
            "api_secret" to "azW21Qp0bpfortIgiNAbAExJNTw"
        )
        MediaManager.init(this, cloudinaryConfig)
    }
}