package com.delighted2wins.souqelkhorda.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.cloudinary.android.MediaManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.core.content.edit
import com.delighted2wins.souqelkhorda.core.AppConstant

@HiltAndroidApp
class App : Application(){
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
        val firebaseAuth = FirebaseAuth.getInstance()
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                handleSignedInUser(firebaseUser.uid)
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    private fun handleSignedInUser(uid: String) {
        val prefs = getSharedPreferences(AppConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val pendingToken = prefs.getString("fcm_token", null)

        if (pendingToken.isNullOrBlank()) return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val firestore = FirebaseFirestore.getInstance()
                val data = mapOf("fcmToken" to pendingToken)
                firestore.collection("users")
                    .document(uid)
                    .set(data, SetOptions.merge())
                    .await()
                prefs.edit { remove("fcm_token") }
            } catch (e: Exception) {
                Log.e("App", "Failed to sync FCM token: ${e.message}", e)
            }
        }
    }
}