package com.esungjae.weather.model.network.data

import androidx.lifecycle.LiveData
import com.esungjae.weather.model.network.Location
import com.esungjae.weather.model.network.Weather
import java.util.*

interface WeatherDataSource {

    fun searchLocations(query: String): LiveData<Result<List<Location>>>
    fun getWeather(woeid: Int, date: Date): LiveData<Result<Weather>>
}