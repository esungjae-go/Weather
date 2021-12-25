package com.esungjae.weather.common

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager

fun Context.isNetworkAvailable(): Boolean {
    val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    return true
}