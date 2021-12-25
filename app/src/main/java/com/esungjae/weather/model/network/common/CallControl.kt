package com.esungjae.weather.model.network.common

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

interface CallControl {
    @Throws(Exception::class)
    fun assertCallable(request: Request)
}

fun OkHttpClient.Builder.setCallControls(controls: List<CallControl>) {
    addInterceptor(CallControlInterceptor(controls))
}

private class CallControlInterceptor(
    private val callControls: List<CallControl>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        callControls.forEach { control ->
            control.assertCallable(request)
        }
        return chain.proceed(request)
    }
}