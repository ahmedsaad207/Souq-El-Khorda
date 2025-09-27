package com.delighted2wins.souqelkhorda.core.internet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ConnectivityObserver {
    val isOnline: LiveData<Boolean>
    fun unregister()
}

class ConnectivityObserverImp(private val context: Context) : ConnectivityObserver {

    private val _isOnline = MutableLiveData<Boolean>()
    override val isOnline: LiveData<Boolean> get() = _isOnline

    private val connectivityReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            _isOnline.postValue(isInternetAvailable(context))
        }
    }

    init {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(connectivityReceiver, filter)
        _isOnline.value = isInternetAvailable(context)
    }

    override fun unregister() {
        context.unregisterReceiver(connectivityReceiver)
    }

    private fun isInternetAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
