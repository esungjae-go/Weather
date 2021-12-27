package com.esungjae.weather.model.network

import com.esungjae.weather.model.network.data.Result
import com.esungjae.weather.model.network.data.WeatherDataSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val dataSource: WeatherDataSource
) {
    suspend fun getLocations(query: String): Result<List<Location>> =
        dataSource.getLocations(query)

    suspend fun getWeather(woeid: Int) = dataSource.getWeather(woeid)
}