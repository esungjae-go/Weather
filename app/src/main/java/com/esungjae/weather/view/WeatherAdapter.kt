package com.esungjae.weather.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esungjae.weather.R
import com.esungjae.weather.databinding.ListItemLocalWeatherDetailBinding
import com.esungjae.weather.databinding.ListItemLocalWeatherHeaderBinding
import com.esungjae.weather.databinding.ListItemLocationWeatherBinding
import com.esungjae.weather.model.network.Weather
import com.esungjae.weather.view.WeatherAdapter.ViewType.HEADER
import com.esungjae.weather.view.WeatherAdapter.ViewType.WEATHER_INFO
import com.esungjae.weather.viewmodel.LocalWeather

class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<LocalWeather>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER) {
            val binding = ListItemLocalWeatherHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            HeaderViewHolder(binding)
        } else {
            val binding = ListItemLocationWeatherBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == WEATHER_INFO) {
            with(holder as ViewHolder) {
                val item = items[position.toItemPos()]
                val context = itemView.context
                city.title.text = item.cityName
                today.fillWeather(context, item.today)
                tomorrow.fillWeather(context, item.tomorrow)
            }
        }
    }

    //show header only when items ready
    override fun getItemCount(): Int = if (items.size > 0) items.size + 1 else 0

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int) = if (position == 0) HEADER else WEATHER_INFO

    private fun Int.toItemPos() = this - 1

    @SuppressLint("NotifyDataSetChanged")
    fun swapItems(_items: List<LocalWeather>?) {
        items.clear()
        _items?.let { items.addAll(it) }
        notifyDataSetChanged()
    }

    private fun ListItemLocalWeatherDetailBinding.fillWeather(context: Context, weather: Weather) {
        val abbr = weather.state_abbr
        Glide.with(context)
            .load("https://www.metaweather.com/static/img/weather/${abbr}.svg")
            .into(icon)
        state.text = weather.state
        temp.text =
            String.format(context.getString(R.string.temperature), weather.temperature.toInt())
        humidity.text =
            String.format(context.getString(R.string.humidify), weather.humidity.toInt())
    }

    object ViewType {
        const val HEADER = 1000
        const val WEATHER_INFO = 2000
    }

    inner class ViewHolder(binding: ListItemLocationWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val city = binding.locationName
        val today = binding.todayWeather
        val tomorrow = binding.tomorrowWeather
    }

    inner class HeaderViewHolder(binding: ListItemLocalWeatherHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)
}