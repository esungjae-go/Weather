package com.esungjae.weather.model.network

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.esungjae.weather.model.network.data.Result
import com.esungjae.weather.model.network.data.Result.Loading
import com.esungjae.weather.model.network.data.WeatherDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val dataSource: WeatherDataSource
) {
    fun searchLocations(query: String): LiveData<Result<List<Location>>> = liveData {
        emit(Loading)
        emitSource(dataSource.searchLocations(query))
    }

    fun getWeather(woeid: Int, date: Date) = liveData {
        emit(Loading)
        emitSource(dataSource.getWeather(woeid, date))
    }
}