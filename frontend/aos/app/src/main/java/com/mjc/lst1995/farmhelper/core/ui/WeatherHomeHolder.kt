package com.mjc.lst1995.farmhelper.core.ui

import androidx.recyclerview.widget.RecyclerView
import com.mjc.lst1995.farmhelper.core.ui.model.Weather
import com.mjc.lst1995.farmhelper.databinding.WeatherHomeHorderBinding

class WeatherHomeHolder(
    private val binding: WeatherHomeHorderBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: Weather) {
        binding.textView.text = weather.information
    }
}
