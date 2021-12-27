package com.esungjae.weather.model.network.data

import com.esungjae.weather.model.network.data.Result.Success


sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

fun <T> Result<T>.getOrNull() = (this as? Success)?.data