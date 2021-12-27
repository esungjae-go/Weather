package com.esungjae.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.esungjae.weather.model.network.Weather
import com.esungjae.weather.model.network.WeatherRepository
import com.esungjae.weather.model.network.data.getOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: WeatherRepository) : ViewModel() {

    private var _query = MutableLiveData<String>()

    private var _loading = MutableLiveData(false)
    var loading: LiveData<Boolean> = _loading

    fun setLocationQuery(query: String) {
        _query.value = query
    }

    fun getLocationQuery(): String? = _query.value

    val localWeathers: LiveData<List<LocalWeather>> by lazy {
        _query.switchMap { query ->
            liveData {
                _loading.value = true
                emit(fetchLocalWeathers(query))
                _loading.value = false
            }
        }
    }

    private suspend fun fetchLocalWeathers(query: String): List<LocalWeather> {
        val result = mutableListOf<LocalWeather>()
        repo.getLocations(query).getOrNull()?.map { location ->
            viewModelScope.async { repo.getWeather(location.woeid) }
        }?.awaitAll()?.map { weather ->
            weather.getOrNull()?.takeIf { it.weathers.size >= 2 }?.let { response ->
                result.add(LocalWeather(response.title, response.weathers[0], response.weathers[1]))
            }
        }
        return result
    }
}

data class LocalWeather(
    val cityName: String,
    val today: Weather,
    val tomorrow: Weather
)