package com.esungjae.weather.common

import retrofit2.Call
import retrofit2.awaitResponse

suspend inline fun <reified T> Call<T>.awaitResponseOrNull(): T? {
    val response = awaitResponse()
    return response.body()
}