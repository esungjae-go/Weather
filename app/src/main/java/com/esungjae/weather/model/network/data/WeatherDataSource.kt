package com.esungjae.weather.model.network.data

import com.esungjae.weather.model.network.Location
import com.esungjae.weather.model.network.Weather
import com.esungjae.weather.model.network.WeatherResponse

interface WeatherDataSource {

    suspend fun getLocations(query: String): Result<List<Location>>
    suspend fun getWeather(woeid: Int): Result<WeatherResponse>
}