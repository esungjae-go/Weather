package com.esungjae.weather.model.network

import android.content.Context
import com.google.gson.annotations.SerializedName
import java.util.Date
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("/api/location/search/")
    fun searchLocation(@Query("query") query: String): Call<List<Location>>

    @GET("/api/location/{woeid}")
    fun getWeather(@Path("woeid") woeid: Int): Call<WeatherResponse>

    companion object {
        @Volatile
        private var instance: WeatherApi? = null

        fun get(context: Context) = instance ?: synchronized(this) {
            instance ?: build(context).also { instance = it }
        }

        private fun build(context: Context): WeatherApi {
            return RetrofitClient.build(context)
        }
    }
}

data class Location(
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("latt_long")
    val lattLong: String,
    val woeid: Int,
    val distance: Int
) {
    fun lattLong(): Pair<Float, Float>? {
        return try {
            lattLong.split(",").takeIf { it.size == 2 }?.let { list ->
                Pair(list[0].toFloat(), list[1].toFloat())
            }
        } catch (e: NumberFormatException) {
            null
        }
    }
}

data class WeatherResponse(
    val title: String,
    @SerializedName("consolidated_weather")
    val weathers: List<Weather>
)

data class Weather(
    @SerializedName("applicable_date")
    val date: Date,
    @SerializedName("weather_state_name")
    val state: String,
    @SerializedName("weather_state_abbr")
    val state_abbr: String,
    @SerializedName("the_temp")
    val temperature: Float,
    val humidity: Float
)