package com.esungjae.weather.model.network

import android.content.Context
import java.util.concurrent.TimeUnit.SECONDS
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val cacheInterceptor = object : Interceptor {
        override fun intercept(chain: Chain): Response {
            val response = chain.proceed(chain.request())
            //cache for 5 mins
            return response.newBuilder().header("Cache-Control", "public, max-age=${(5 * 60)}")
                .removeHeader("Pragma").build()
        }
    }

    inline fun <reified T> build(context: Context): T = Retrofit.Builder().run {
        val logger = HttpLoggingInterceptor().apply {
            level = Level.BODY
        }
        OkHttpClient.Builder().apply {
            connectTimeout(10_000, SECONDS)
            readTimeout(10_000, SECONDS)
            writeTimeout(10_000, SECONDS)
            addInterceptor(logger)
            addNetworkInterceptor(cacheInterceptor)
            cache(Cache(context.cacheDir, 10 * 1024 * 1024)) //10MB cache
        }.build().let { client(it) }
        baseUrl("https://www.metaweather.com/")
        addConverterFactory(GsonConverterFactory.create())
        val retrofit = build()
        retrofit.create(T::class.java)
    }
}