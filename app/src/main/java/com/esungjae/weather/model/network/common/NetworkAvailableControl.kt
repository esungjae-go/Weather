package com.esungjae.weather.model.network.common

import android.content.Context
import com.esungjae.weather.common.isNetworkAvailable
import okhttp3.Request
import java.net.ConnectException

class NetworkAvailableControl(private val context: Context) : CallControl {
    override fun assertCallable(request: Request) {
        if (!context.isNetworkAvailable()) {
            throw NetworkUnavailableException(request)
        }
    }
}

class NetworkUnavailableException(request: Request) : ConnectException(
    "Network not available on ${request.url()}"
)