package com.example.wltest.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


fun Context.isNetworkAvailable() : Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
    return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
