package com.esungjae.weather.model.network

import android.content.Context
import com.esungjae.weather.model.network.data.Result
import com.esungjae.weather.model.network.data.Result.Error
import com.esungjae.weather.model.network.data.Result.Success
import com.esungjae.weather.model.network.data.WeatherDataSource
import retrofit2.HttpException
import retrofit2.awaitResponse

class RemoteWeatherDataSource(
    context: Context,
    private val api: WeatherApi = WeatherApi.get(context)
) : WeatherDataSource {

    override suspend fun getLocations(query: String): Result<List<Location>> {
        val response = api.searchLocation(query).awaitResponse()
        return response.body()?.let { Success(it) } ?: Error(HttpException(response))
    }

    override suspend fun getWeather(woeid: Int): Result<WeatherResponse> {
        val response = api.getWeather(woeid).awaitResponse()
        return response.body()?.let { Success(it) } ?: Error(HttpException(response))
    }
}