package com.nasa.apod.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.annotation.VisibleForTesting

internal object NetworkMonitor {

    fun startNetworkCallback(appContext: Context) {
        val cm: ConnectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }

    fun stopNetworkCallback(appContext: Context) {
        val cm: ConnectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(networkCallback)
    }

    @VisibleForTesting
    internal val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            Variables.isNetworkConnected = true
        }

        override fun onLost(network: Network) {
            Variables.isNetworkConnected = false
        }
    }
}