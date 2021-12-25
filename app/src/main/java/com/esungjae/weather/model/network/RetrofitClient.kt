package com.esungjae.weather.model.network

import retrofit2.Retrofit

object RetrofitClient {

    inline fun <reified T> build(): T = Retrofit.Builder().run {
        val clazz = T::class.java
        val retrofit = build()
        retrofit.create(clazz)
    }
}