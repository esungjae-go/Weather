package com.esungjae.weather.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esungjae.weather.databinding.ListItemLocationWeatherBinding
import com.esungjae.weather.view.WeatherAdapter.ViewHolder

class WeatherAdapter : RecyclerView.Adapter<ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemLocationWeatherBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(binding: ListItemLocationWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.locationName
        val today = binding.todayWeather
        val tomorrow = binding.tomorrowWeather
    }
}