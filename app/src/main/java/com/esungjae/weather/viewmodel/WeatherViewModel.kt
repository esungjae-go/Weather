package com.esungjae.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esungjae.weather.model.network.Location
import com.esungjae.weather.model.network.Weather
import com.esungjae.weather.model.network.WeatherRepository
import com.esungjae.weather.model.network.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: WeatherRepository) : ViewModel() {

    fun searchLocations(query: String): LiveData<Result<List<Location>>> =
        repo.searchLocations(query)

    fun getWeather(woeid: Int, date: Date): LiveData<Result<Weather>> = repo.getWeather(woeid, date)
}